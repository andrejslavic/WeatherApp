import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class WeatherService {
    private final HttpClient client;
    private final Gson gson;

    public WeatherService(){
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public WeatherResponse fetchCurrentWeather(double latiude, double longitude) throws IOException, InterruptedException{
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.2f&longitude=%.2f&current_weather=true",
                latiude, longitude
        );

        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            return gson.fromJson(response.body(), WeatherResponse.class);
        } else {
            throw new IOException("Error");
        }
    }
}
