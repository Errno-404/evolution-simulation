package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("config.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("config.css")).toExternalForm();
            scene.getStylesheets().add(css);


            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Konfiguracja symulacji");


            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}






