package pl.mareksowa.weatherApp.model.utils;

import javafx.application.Platform;
import pl.mareksowa.weatherApp.model.serice.WeatherService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utils { //Utils contains common methods

    public static String readWebsideContext(String url){
        StringBuilder builder = new StringBuilder();

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();//rzuujemy odrazu na http
            InputStream inputStream = httpURLConnection.getInputStream();

            int response;
            while ((response = inputStream.read()) != -1){ // read zwraca -1 gdy nie ma co juz czytac (response ma szybka inicjalizacje)
                builder.append((char)response);
            }

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString(); //wracamy to co sobie utworzylismy jako string
    }


    public static void closeApp(){
        WeatherService.terminateExecutorService(); //before close terminate all threads
        Platform.exit();
    }

}
