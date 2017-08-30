package pl.mareksowa.weatherApp.model.serice;


import pl.mareksowa.weatherApp.model.WeatherData;

public interface IWeatherObserver {//obserwator
    void onWeatherUpdate(WeatherData data);
}
