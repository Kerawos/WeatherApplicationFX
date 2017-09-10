package pl.mareksowa.weatherApp.controllers;

import pl.mareksowa.weatherApp.model.WeatherStat;
import pl.mareksowa.weatherApp.model.dao.IWeatherStatDao;
import pl.mareksowa.weatherApp.model.dao.impl.WeatherStatDaoImpl;
import pl.mareksowa.weatherApp.model.utils.DatabaseConnector;
import pl.mareksowa.weatherApp.model.utils.Utils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StatController implements Initializable {

    @FXML
    BarChart<String, Number> chartWeather;

    @FXML
    ListView listOfCity;

    @FXML
    Button btnBack;

    @FXML
    Label lblAverageTemp;

    //private DatabaseConnector connector = DatabaseConnector.getInstance();
    private IWeatherStatDao iWeatherStatDao = new WeatherStatDaoImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButtonShowMain();
        loadCityNames();
        registerOnClickItemOnList();
    }

    //back to main window
    public void registerButtonShowMain(){
        btnBack.setOnMouseClicked(e-> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
                stage.setScene(new Scene(root, 600, 400));
                stage.setOnCloseRequest(e2-> Utils.closeApp());

            } catch (IOException e3) {
                e3.printStackTrace();
            }
        });
    }

    private void loadCityNames(){
        listOfCity.setItems(FXCollections.observableList(iWeatherStatDao.getAllCities()));
    }

    private void loadChart(String city){
        XYChart.Series series = new XYChart.Series();
        series.setName(city);
        int counter = 0;
        for (WeatherStat weatherStat : iWeatherStatDao.getLastSixStat(city)){
            series.getData().add(new XYChart.Data("" + counter, weatherStat.getTemp()));
            counter++;
        }
        chartWeather.getData().clear();
        chartWeather.getData().add(series);
    }

    private void registerOnClickItemOnList(){
        listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadChart((String) newValue);
            System.out.println("Chart for:" + newValue + " loaded");
            iWeatherStatDao.calcAverageTempForSelectedCity((String)newValue);
            lblAverageTemp.setText(String.valueOf(Math.round(iWeatherStatDao.calcAverageTempForSelectedCity((String)newValue))*100/100) + "C"); //add round to two points
        });
        //listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> iWeatherStatDao.calcAverageTempForSelectedCity((String)newValue));
        //lblAverageTemp.setText(String.valueOf(iWeatherStatDao.calcAverageTempForSelectedCity((String) listOfCity.getSelectionModel().getSelectedItem())));
        //lblAverageTemp.setText((String)iWeatherStatDao.calcAverageTempForSelectedCity(listOfCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> (String)newValue));
    }



}
