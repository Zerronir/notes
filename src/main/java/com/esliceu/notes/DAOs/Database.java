package com.esliceu.notes.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;

    public static Connection getConnection() {
        try{
            // url windows
            // String url = "jdbc:sqlite:D:/FPGSDAW/Segundo/Servidor/jdbc/practica.db";

            // url macbook
            String url = "jdbc:sqlite:/Users/raulmiralles/Documents/FPGSDAW/SEGUNDO/Servidor/Practicas/notes/practica.db";

            if(connection == null){
                Class.forName("org.sqlite.JDBC");
                //Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url);
            }

            return connection;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}