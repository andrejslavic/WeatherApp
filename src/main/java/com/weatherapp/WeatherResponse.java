package com.weatherapp;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("current_weather")
    public CurrentWeather currentWeather;

    public static class CurrentWeather {
        public double temperature;
        public double windspeed;
        public int weathercode;
    }
}
