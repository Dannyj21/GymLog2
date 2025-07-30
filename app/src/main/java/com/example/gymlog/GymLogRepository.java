
package com.example.gymlog;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class for GymLog and User entities.
 * Handles database operations on a background thread.
 */
public class GymLogRepository {

    private final GymLogDao gymLogDao;
    private final UserDao userDao;
    private final ExecutorService executorService;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getInstance(application);
        gymLogDao = db.gymLogDao();
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }


    public void insertGymLog(GymLog log) {
        executorService.execute(() -> gymLogDao.insert(log));
    }


    public LiveData<List<GymLog>> getLogsByUser(int userId) {
        return gymLogDao.getLogsByUser(userId);
    }


    public LiveData<List<GymLog>> getAllLogs() {
        return gymLogDao.getAllLogs();
    }


    public void insertUser(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    // Fetch all users
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }
}