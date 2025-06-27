import java.io.IOException;

public class WeatherFetcher {
    public static void main(String[] args){

        WeatherService weatherService = new WeatherService();

        try{

            WeatherResponse response = weatherService.fetchCurrentWeather(52.52, 13.41);

            double temperature = response.currentWeather.temperature;
            double windspeed = response.currentWeather.windspeed;

            System.out.println("Current temperature: "+temperature);
            System.out.println("Current windspeed: "+windspeed);

        } catch (IOException | InterruptedException e) {
            System.err.println("ERROR");
            e.printStackTrace();
        }

    }
}
