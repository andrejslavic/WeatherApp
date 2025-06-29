module com.weatherapp {
    requires javafx.controls;

    // This is the crucial line: it allows JavaFX to access your assets folder.
    opens assets to javafx.graphics;
}
