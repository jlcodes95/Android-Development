package com.example.mymessenger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserList {

    private static ArrayList<User> users;

    public UserList(){
        if (users == null){
            users = new ArrayList<User>();
        }
    }

    public void addUser(User user){

        users.add(user);
    }

    public void removeUser(String username){
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(username)){
                users.remove(i);
                break;
            }
        }
    }

    public boolean checkIfUserExists(String username){
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public User validateUser(String username, String password) throws Exception{
        for (int i = 0; i < users.size(); i++){
            User user = users.get(i);
            if (user.getUsername().equals(username)){
                if (user.getPassword().equals(password)){
                    return user;
                }else{
                    throw new Exception("Your username and password doesn't match.");
                }
            }
        }
        return null;
    }
}
