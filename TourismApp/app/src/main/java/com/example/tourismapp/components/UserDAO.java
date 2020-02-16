package com.example.tourismapp.components;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {

    @Insert
    public void addUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("UPDATE users SET password=:password WHERE id=:id")
    public void updateUserById(int id, String password);

    @Query("SELECT * FROM users WHERE id=:id")
    public List<User> getUserById(int id);

    @Query("SELECT * FROM users WHERE username=:username")
    public List<User> getUserByUsername(String username);

    @Query("SELECT * FROM users")
    public List<User> getUserList();
}
