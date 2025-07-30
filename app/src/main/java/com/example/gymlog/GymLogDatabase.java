package com.example.gymlog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GymLog.class, User.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    public abstract GymLogDao gymLogDao();
    public abstract UserDao userDao();

    private static volatile GymLogDatabase INSTANCE;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static GymLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GymLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GymLogDatabase.class, "gymlog_database")
                            .addCallback(roomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback roomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Pre-populate database with a default user
            databaseWriteExecutor.execute(() -> {
                User defaultUser = new User("admin", "1234");
                INSTANCE.userDao().insert(defaultUser);
            });
        }
    };
}
