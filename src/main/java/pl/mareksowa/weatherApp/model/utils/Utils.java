package pl.mareksowa.weatherApp.model.utils;

import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utils { //Utils contains common methods

    public static String readWebsiteContext(String url){
        StringBuilder builder = new StringBuilder();

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();//cast to HTTP
            InputStream inputStream = httpURLConnection.getInputStream();
            int response;
            while ((response = inputStream.read()) != -1){ // back read field
                builder.append((char)response);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString(); //return our build string
    }


    public static void closeApp(){
        Platform.exit();
        System.out.println("App closing..");
        System.exit(0);
    }

}
