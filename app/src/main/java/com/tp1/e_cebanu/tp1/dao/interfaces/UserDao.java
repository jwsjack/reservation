package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.User;

import java.util.List;

/**
 * Created by User on 27.02.2017.
 */

/**
 * Here go all methods for operations with User objects
 * Can be copied for any other objects
 * Can be modified for any purpose
 */
public interface UserDao {
    void insertUser(User user);
    void deleteUser(User user);
    void addUser(User user);
    User getUserById(int id);
    User getUserByName(String name);
    List<User> getUsers();
}
