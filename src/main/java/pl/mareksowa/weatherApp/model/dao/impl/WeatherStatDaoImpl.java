package pl.mareksowa.weatherApp.model.dao.impl;

import pl.mareksowa.weatherApp.model.WeatherStat;
import pl.mareksowa.weatherApp.model.dao.IWeatherStatDao;
import pl.mareksowa.weatherApp.model.utils.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class WeatherStatDaoImpl implements IWeatherStatDao {

    private DatabaseConnector connector = DatabaseConnector.getInstance();

    @Override
    public void saveStat(WeatherStat weatherStat) {
        PreparedStatement preparedStatement = connector.getNewPreparedStatement("INSERT INTO weather VALUES(?,?,?)");
        try {
            preparedStatement.setInt(1,0);
            preparedStatement.setString(2, weatherStat.getCityName());
            preparedStatement.setInt(3, weatherStat.getTemp()-273);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<WeatherStat> getLastSixStat(String cityName) {
        List<WeatherStat> weatherStatList = new ArrayList<>();
        PreparedStatement preparedStatement = connector.getNewPreparedStatement("SELECT * FROM weather WHERE city = ? ORDER BY id DESC LIMIT 6");
        try {
            preparedStatement.setString(1, cityName);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                weatherStatList.add(new WeatherStat(resultSet.getString("city"), resultSet.getInt("temp")));
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherStatList;
    }

    @Override
    public List<String> getAllCities() {
        List<String> cityNames = new ArrayList<>();
        PreparedStatement preparedStatement = connector.getNewPreparedStatement("SELECT DISTINCT city FROM weather");

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cityNames.add(resultSet.getString("city"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityNames;
    }


    @Override
    public Double calcAverageTempForSelectedCity(String city){


        List<Double> temps = new ArrayList<>();
        PreparedStatement preparedStatement = connector.getNewPreparedStatement("SELECT temp FROM weather WHERE city = ?");
        try {
            preparedStatement.setString(1, city);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                temps.add(resultSet.getDouble("temp")); //have
            }

            //temps.forEach(l-> System.out.println(l)); //for debugging

        } catch (SQLException e) {
            e.printStackTrace();
        }
        OptionalDouble result = temps.stream().mapToDouble(a->a).average();
        double d = result.orElseThrow(IllegalStateException::new);
        //System.out.println("Srednia to: " + temps.stream().mapToDouble(a->a).average());
        //System.out.println("Srednia 2 to: " + d);
        return d;
    }
}


