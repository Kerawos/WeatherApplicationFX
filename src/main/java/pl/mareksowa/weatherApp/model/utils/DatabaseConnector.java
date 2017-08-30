package pl.mareksowa.weatherApp.model.utils;

import java.sql.*;

public class DatabaseConnector {

    private static DatabaseConnector ourInstance = new DatabaseConnector(); // tworzymy prywatna instancje klasy odarrazu przy deklaracji (SINGLETON)

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }
    //dane dostepowe do bazu statyczne
    private static final String SQL_LINK = "jdbc:mysql://5.135.218.27:3306/Marek?characterEncoding=utf8";
    private static final String SQL_USER = "Marek";
    private static final String SQL_PASS = "Krakuski1";
    private static final String SQL_CLASS = "com.mysql.jdbc.Driver";

    //przechowuje polaczenie
    private Connection connection;

    //prywatny konstruktor by nie tworzyc instancji tej kalsu - przy SINGLETONIE
    private DatabaseConnector(){
        connect();
        System.out.println("Polaczono");
    }

    private void connect(){
        try {
            Class.forName(SQL_CLASS).newInstance();
            connection = DriverManager.getConnection(SQL_LINK, SQL_USER, SQL_PASS);
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


    //statementy sluza do zapytan do serwerow
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
