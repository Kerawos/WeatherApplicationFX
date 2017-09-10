package pl.mareksowa.weatherApp.model.serice;

import org.json.JSONObject;
import pl.mareksowa.weatherApp.Config;
import pl.mareksowa.weatherApp.model.utils.Utils;
import pl.mareksowa.weatherApp.model.WeatherData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService {

    //generate singleton
    private static WeatherService INSTANCE = new WeatherService(); //one instance allowed here
    public static WeatherService getService(){ // metoda zwracajaca instancje klasy
        return INSTANCE;
    }
    private static List<IWeatherObserver> observerList;
    private static ExecutorService executorService;


    private WeatherService(){ //to prevent create more than one class
        executorService = Executors.newFixedThreadPool(2);
        observerList = new ArrayList<>(); // generate observer list to later update/notify all obserwers
    }

    //kill all threads
    public static void terminateExecutorService(){
        executorService.shutdown();
    }

    //add observer to obserwer lists
    public void registerObserver(IWeatherObserver observer){
        observerList.add(observer);
    }

    //remove obserwer
    public static void removeObserver() {
        observerList.remove(observerList.size()-1); // remove last obserwer to prevent ++ infinite
    }

    //notify all obserwvrs
    private void notifyObservers(WeatherData data){
        for (IWeatherObserver iWeatherObserver : observerList) {
            iWeatherObserver.onWeatherUpdate(data);
        }
    }

    //get data from API and save it in JSon
    public void init(final String city){
        Runnable taskInit = () -> {
            String text = Utils.readWebsiteContext(Config.API_URL + city + "&appid=" + Config.APP_KEY);
            parseJsonFromString(text);
        };
        executorService.execute(taskInit);
    }

    private void parseJsonFromString(String text) { //JSON object korzysta z bilbioteki JSON ktora zaimplementowaliscmy
        WeatherData data = new WeatherData();
        JSONObject root = new JSONObject(text);
        JSONObject main = root.getJSONObject("main");
            data.setTemp(main.getDouble("temp"));
            data.setPressure(main.getInt("pressure"));
            data.setHumidity(main.getInt("humidity"));

        JSONObject cloudsObject = root.getJSONObject("clouds");
            data.setClouds(cloudsObject.getInt("all"));
        notifyObservers(data);
    }

}
