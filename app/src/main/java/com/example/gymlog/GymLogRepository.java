
package com.example.gymlog;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GymLogRepository {

    private final GymLogDao gymLogDao;
    private final UserDao userDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        gymLogDao = db.gymLogDao();
        userDao = db.userDao();
    }

    public void insertGymLog(GymLog gymLog) {
        executorService.execute(() -> gymLogDao.insert(gymLog));
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
}