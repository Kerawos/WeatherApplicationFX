package pl.mareksowa.weatherApp.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.mareksowa.weatherApp.model.WeatherData;
import pl.mareksowa.weatherApp.model.WeatherStat;
import pl.mareksowa.weatherApp.model.dao.IWeatherStatDao;
import pl.mareksowa.weatherApp.model.dao.impl.WeatherStatDaoImpl;
import pl.mareksowa.weatherApp.model.serice.IWeatherObserver;
import pl.mareksowa.weatherApp.model.serice.WeatherService;
import pl.mareksowa.weatherApp.model.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements IWeatherObserver, Initializable {

    //FX declaration
    @FXML
    TextField txtInputCity;

    @FXML
    Button btnShow;

    @FXML
    TextArea txtWeatherInfo;

    @FXML
    ProgressIndicator progressLoad;

    @FXML
    Button btnShowStat;

    private String lastCityName;

    //Instanes of usable class
    private WeatherService weatherService = WeatherService.getService();
    private IWeatherStatDao iWeatherStatDao = new WeatherStatDaoImpl();


    @Override //register observer
    public void onWeatherUpdate(WeatherData data) {
        txtWeatherInfo.setText("Temp:" + ((Math.round(data.getTemp()-273)*100)/100) +"C, Press:" + data.getPressure() + "Hpa, Humid:" + data.getHumidity() + "%, Clouds:" + data.getClouds()+"%.");
        progressLoad.setVisible(false);
        iWeatherStatDao.saveStat(new WeatherStat(lastCityName, (int) data.getTemp()));
    }


    @Override //what will be loaded
    public void initialize(URL location, ResourceBundle resources) {
        weatherService.registerObserver(this); //register obserwer
        registerShowButtonAction(); // register button
        registerEnterListener(); // register button (hit enter on input city textfield)
        registerButtonShowStats(); // register button
        txtWeatherInfo.setWrapText(true); // tip for area field to wrap text in necessary
    }

    //replace stages (new window with statistic
    public void registerButtonShowStats(){
        btnShowStat.setOnMouseClicked(event-> {
            Stage stage = (Stage) btnShowStat.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("stat.fxml"));
                stage.setScene(new Scene(root, 600, 400));
                stage.setOnCloseRequest(event1-> Utils.closeApp());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void registerShowButtonAction(){
        btnShow.setOnMouseClicked(e-> preparedRequestAndClear());
    }


    private void registerEnterListener(){
        txtInputCity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                preparedRequestAndClear();
            }
        });
    }

    //load progress bar, call weather method, update result in database
    private void preparedRequestAndClear(){
        lastCityName = txtInputCity.getText();
        progressLoad.setVisible(true);
        weatherService.init(txtInputCity.getText());
        txtInputCity.clear();
    }


}
