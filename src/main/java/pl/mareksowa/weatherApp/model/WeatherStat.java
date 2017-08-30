package pl.mareksowa.weatherApp.model;

public class WeatherStat {
    private String cityName;
    private int temp;

    public WeatherStat(String cityName, int temp) {
        this.cityName = cityName;
        this.temp = temp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
