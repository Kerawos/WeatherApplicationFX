package pl.mareksowa.weatherApp.model;

//klasa czysto nosnikowa (tlyko do przysylania danych
public class WeatherData {

    private double temp;
    private int humidity;
    private int pressure;
    private int clouds;

    public WeatherData(){

    }

    public WeatherData(double temp, int humidity, int pressure, int clouds) {

        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.clouds = clouds;
    }



    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }


}
