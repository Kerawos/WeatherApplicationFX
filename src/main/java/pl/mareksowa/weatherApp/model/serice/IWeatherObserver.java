package pl.mareksowa.weatherApp.model.serice;


import pl.mareksowa.weatherApp.model.WeatherData;

//observer interface which we can implements
public interface IWeatherObserver {
    void onWeatherUpdate(WeatherData data);
}
