package com.weatherapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.http.HttpResponse;

public class LocationService {
    private final HttpClient client;
    private final Gson gson;

    public LocationService(){
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public LocationResult fetchCoordinates(String cityName) throws IOException, InterruptedException{
        String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        String url = String.format(
                "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json",
                encodedCityName
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200) {
            LocationResponse locationResponse = gson.fromJson(response.body(), LocationResponse.class);
            if(locationResponse != null && locationResponse.results != null && !locationResponse.results.isEmpty()){
                return locationResponse.results.get(0);
            } else {
                return null;
            }
        } else {
            throw new IOException("Error");
        }
    }
}
