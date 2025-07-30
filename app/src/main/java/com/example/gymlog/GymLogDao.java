
package com.example.gymlog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * DAO for GymLog entity.
 */
@Dao
public interface GymLogDao {

    // Insert a new gym log, returning the row ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GymLog gymLog);

    // Fetch all logs
    @Query("SELECT * FROM gym_logs")
    LiveData<List<GymLog>> getAllLogs();

    // Fetch logs by userId
    @Query("SELECT * FROM gym_logs WHERE userId = :userId")
    LiveData<List<GymLog>> getLogsByUser(int userId);
}