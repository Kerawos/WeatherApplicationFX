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

    //SINGLETON  -tworzona jest tylko jedna instancja klasy.
    //serwisy - maja za zadanie przyjac zadanie cos z nim zrobic i tyle. np. serwis sms, przesylamy mu tekst a serwis tylko wysyla sms.

    private static WeatherService INSTANCE = new WeatherService(); //tylko jedna instancja (INSTANCE - staticki piszemy z duzych liter)
    public static WeatherService getService(){ // metoda zwracajaca instancje klasy
        return INSTANCE;
    }
    private List<IWeatherObserver> observerList;
    private static ExecutorService executorService;


    private WeatherService(){ //prywatny konstruktor, nie ma mozliwosci tworzenia instancji z innych klas
        executorService = Executors.newFixedThreadPool(2);
        observerList = new ArrayList<>(); // tworzymy liste obserwujacych z interfejsu
    }

    public static void terminateExecutorService(){
        executorService.shutdown();
    }

    //dodajemy obserwerow do naszej listy
    public void registerObserver(IWeatherObserver observer){
        observerList.add(observer);
    }

    //informujemy obserwatorow
    private void notifyObserwers(WeatherData data){
        for (IWeatherObserver iWeatherObserver : observerList) {
            iWeatherObserver.onWeatherUpdate(data);
        }
    }


    public void init(final String city){
        Runnable taskInit = new Runnable() {
            @Override
            public void run() {
                String text = Utils.readWebsideContext(Config.API_URL + city + "&appid=" + Config.APP_KEY);
                parseJsonFromString(text);
            }
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
        notifyObserwers(data);
    }

}
