package com.weatherapp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeatherAppGui extends Application {

    private final LocationService locationService = new LocationService();
    private final WeatherService weatherService = new WeatherService();

    private Label cityNameLabel;
    private ImageView weatherConditionImage;
    private Label tempLabel;
    private Label weatherDescLabel;

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

        searchButton.setOnAction(event -> {
            String cityName = searchField.getText();
            if (cityName != null && !cityName.trim().isEmpty()) {
                // Pozivamo metodu koja će obaviti posao u pozadini
                fetchAndDisplayWeather(cityName);
            } else {
                showError("Please enter a city name.");
            }
        });

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, searchButton);

        cityNameLabel = new Label("Enter city name");
        cityNameLabel.getStyleClass().add("city-name-label");

        weatherConditionImage = new ImageView();
        weatherConditionImage.setFitWidth(150);
        weatherConditionImage.setFitHeight(150);

        tempLabel = new Label("-- C");
        tempLabel.getStyleClass().add("temp-label");

        weatherDescLabel = new Label("");
        weatherDescLabel.getStyleClass().add("weather-desc-label");

        root.getChildren().addAll(searchBox, cityNameLabel, weatherConditionImage, tempLabel, weatherDescLabel);

        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchAndDisplayWeather(String cityName) {
        new Thread(() -> {
            try {
                LocationResult location = locationService.fetchCoordinates(cityName);
                if (location == null) {
                    Platform.runLater(() -> showError("Could not find the city"));
                    return;
                }

                WeatherResponse weather = weatherService.fetchCurrentWeather(location.latitude, location.longitude);
                if (weather == null) {
                    Platform.runLater(() -> showError("Could not fetch weather data."));
                    return;
                }

                Platform.runLater(() -> updateUI(location, weather));
            } catch (Exception e) {
                Platform.runLater(() -> showError("An unkown error occurred."));
                e.printStackTrace();
            }
        }).start();
    }

    private void updateUI(LocationResult location, WeatherResponse weather){
        cityNameLabel.setText(location.name);
        tempLabel.setText(weather.currentWeather.temperature + " °C");

        String desc = getWeatherDescription(weather.currentWeather.weathercode);
        String imagePath = getWeatherImagePath(weather.currentWeather.weathercode);

        weatherDescLabel.setText(desc);
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        weatherConditionImage.setImage(image);
    }

    private String getWeatherDescription(int weatherCode) {
        return switch (weatherCode) {
            case 0 -> "Clear sky";
            case 1 -> "Mainly clear";
            case 2 -> "Partly cloudy";
            case 3 -> "Overcast";
            case 45, 48 -> "Fog";
            case 51, 53, 55 -> "Drizzle";
            case 61, 63, 65 -> "Rain";
            case 71, 73, 75 -> "Snow fall";
            case 80, 81, 82 -> "Rain showers";
            case 85, 86 -> "Snow showers";
            case 95 -> "Thunderstorm";
            default -> "Unknown";
        };
    }

    private String getWeatherImagePath(int weatherCode) {
        if (weatherCode == 0) {
            return "/assets/sunny-day.png";
        } else if (weatherCode >= 1 && weatherCode <= 3) {
            return "/assets/cloudy.png";
        } else if ((weatherCode >= 51 && weatherCode <= 67) || (weatherCode >= 80 && weatherCode <= 82)) {
            return "/assets/rainy.png";
        } else if ((weatherCode >= 71 && weatherCode <= 77) || (weatherCode >= 85 && weatherCode <= 86)) {
            return "/assets/snowflake.png";
        } else if (weatherCode >= 95 && weatherCode <= 99) {
            return "/assets/thunder.png";
        } else {
            return "/assets/cloudy.png";
        }
    }


    private void showError(String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args){
        launch(args);
    }
}
