package pl.mareksowa.weatherApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.mareksowa.weatherApp.model.utils.Utils;


public class AppStarter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public AppStarter(){
    }

    //main section to start application
    public void start(Stage primaryStage) throws Exception {
        //javaFX loader
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setTitle("Weather Application 1.0 by Marek Sowa 2017");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Utils.closeApp());
        primaryStage.show();
    }
}
