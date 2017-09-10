package pl.mareksowa.weatherApp.model.utils;

import pl.mareksowa.weatherApp.Config;

import java.sql.*;

public class DatabaseConnector {

    private static DatabaseConnector ourInstance = new DatabaseConnector(); // private instance Singleton

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }


    //save connect
    private Connection connection;

    //Singleton constructor
    private DatabaseConnector(){
        connect();
        System.out.println("Connect");
    }

    private void connect(){
        try {
            Class.forName(Config.SQL_CLASS).newInstance();
            connection = DriverManager.getConnection(Config.SQL_LINK, Config.SQL_USER, Config.SQL_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //sstatement for SQL orders
    public PreparedStatement getNewPreparedStatement(String sql){
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Statement getStatement(){
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
