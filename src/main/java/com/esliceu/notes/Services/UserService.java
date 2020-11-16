package com.esliceu.notes.Services;

import com.esliceu.notes.Models.User;

import java.util.List;

public interface UserService {
    List<User> getAll(int userId);

    User getFromLogin(String email, String pass);

    int getUserId(String name);

    boolean addUser(User u);

    void deleteUser(User u);

    void update(User u);

    boolean checkMail(String email);
}
