package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll(int userId);

    boolean addUser(User u);

    void deleteUser(User u);

    void update(User u);

    int getUserId(String name);

    User getFromLogin(String email, String pass);
}
