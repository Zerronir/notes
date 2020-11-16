package com.esliceu.notes.Services;

import com.esliceu.notes.DAOs.UserDAO;
import com.esliceu.notes.DAOs.UserServiceAccess;
import com.esliceu.notes.Models.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll(int userId) {
        UserDAO ud = new UserServiceAccess();
        return ud.getAll(userId);
    }

    @Override
    public User getFromLogin(String email, String pass) {
        UserDAO ud = new UserServiceAccess();
        return ud.getFromLogin(email, pass);
    }

    @Override
    public int getUserId(String name) {
        return 0;
    }

    @Override
    public boolean addUser(User u) {
        UserDAO ud = new UserServiceAccess();
        return ud.addUser(u);
    }

    @Override
    public void deleteUser(User u) {

    }

    @Override
    public void update(User u) {

    }

    @Override
    public boolean checkMail(String email) {
        UserDAO ud = new UserServiceAccess();
        return ud.checkMail(email);
    }
}
