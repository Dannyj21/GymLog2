
package com.example.gymlog;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GymLogDao {

    @Insert
    void insert(GymLog gymLog);

    @Query("SELECT * FROM gym_logs WHERE userId = :userId")
    List<GymLog> getLogsForUser(int userId);

    @Query("DELETE FROM gym_logs")
    void deleteAll();
}