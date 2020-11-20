package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserServiceAccess implements UserDAO {
    @Override
    public List<User> getAll(int userId) {
        List<User> userList = new ArrayList<>();

        try{

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE userId != ?");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int gotuserId = rs.getInt("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");

                User u = new User(
                  gotuserId,
                  username,
                  email,
                  ""
                );

                userList.add(u);

            }

            ps.close();
            rs.close();
            Database.closeConnection();

        }catch (Exception e) {
            return null;
        }
        return userList;
    }

    @Override
    public boolean addUser(User u) {

        try{

            // Setting up all values
            String username = u.getName();
            String email = u.getEmail();
            String password = u.getPass();

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c!=null;

            // Executing query
            PreparedStatement ps = c.prepareStatement("INSERT INTO users (username, email, password) VALUES (?,?,?)");
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.execute();
            ps.close();
            Database.closeConnection();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void deleteUser(User u) {

    }

    @Override
    public void update(User u) {

    }

    @Override
    public int getUserId(String name) {
        return 0;
    }

    @Override
    public User getFromLogin(String email, String pass) {


        try{

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int id = rs.getInt("userId");
                String username = rs.getString("username");
                String useremail = rs.getString("email");
                String userpass = rs.getString("password");

                User user = new User(id, username, useremail, userpass);

                return user;
            }

            ps.close();
            rs.close();
            Database.closeConnection();
        }catch (Exception e){
            return null;
        }

        return null;
    }

    @Override
    public boolean checkMail(String email) {

        try{

            Connection c = Database.getConnection();
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("SELECT email FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.getString("email") != null){
                ps.close();
                rs.close();
                Database.closeConnection();
                return true;
            } else {
                ps.close();
                rs.close();
                Database.closeConnection();
                return false;
            }



        }catch (Exception e){
            return true;
        }


    }
}
