
package com.example.gymlog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * DAO for User entity.
 */
@Dao
public interface UserDao {

    // Insert a new user, returning the row ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    // Fetch all users
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    // Fetch a single user by ID
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    LiveData<User> getUserById(int userId);

    // Optional: Fetch a user by username
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User getUserByCredentials(String username, String password);
}