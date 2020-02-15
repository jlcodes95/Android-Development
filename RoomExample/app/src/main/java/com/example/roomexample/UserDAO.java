package com.example.roomexample;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {

    @Insert
    public void addUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("UPDATE users SET first_name = :firstName, last_name = :lastName, email = :email WHERE id = :id")
    public void updateUser(int id, String firstName, String lastName, String email);

    @Query("SELECT * FROM users")
    public List<User> getUsers();
}
