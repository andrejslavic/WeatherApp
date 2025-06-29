package com.weatherapp;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;


public class WeatherAppGui extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        TextField searchField = new TextField();
        searchField.setPromptText("Search location...");
        searchField.getStyleClass().add("search-field");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("search-button");

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, searchButton);

        Label cityNameLabel = new Label("Berlin");
        cityNameLabel.getStyleClass().add("city-name-label");

        Image image = new Image(getClass().getResource("/assets/cloudy.png").toExternalForm());
        ImageView weatherConditionImage = new ImageView(image);

        weatherConditionImage.setFitWidth(150);
        weatherConditionImage.setFitHeight(150);

        Label tempLabel = new Label("21 C");
        tempLabel.getStyleClass().add("temp-label");

        Label weatherDescLabel = new Label("Cloudy");
        weatherDescLabel.getStyleClass().add("weather-desc-label");

        root.getChildren().addAll(searchBox, cityNameLabel, weatherConditionImage, tempLabel, weatherDescLabel);

        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
