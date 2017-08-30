package pl.mareksowa.weatherApp.model.dao;

import pl.mareksowa.weatherApp.model.WeatherStat;

import java.util.List;
import java.util.OptionalDouble;

//metody na SQL
public interface IWeatherStatDao {
    void saveStat(WeatherStat weatherStat);
    List<WeatherStat> getLastSixStat(String cityName);
    List<String> getAllCities();
    Double calcAverageTempForSelectedCity(String city);
}
