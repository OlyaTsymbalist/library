package com.example.dbjavafx;

import java.sql.*;


/**
 * Connect to Database
 */
public class ConnectionFactory {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbntu";
    public static final String DB_USER = "DBNtu"; //ваш користувач, у нас root
    public static final String DB_PASSWORD = "kal1Root!";   // ваш пароль
    public static Connection connection;
    /**

     * Get a connection to database

     * @return Connection object

     */

    public static Connection getConnection() throws SQLException {

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("success connection");
        } catch (SQLException ex) {

            throw new RuntimeException("Error connecting to the database", ex);
        }
        return connection;
    }

}
