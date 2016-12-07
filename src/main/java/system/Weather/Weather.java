package system.Weather;

import com.google.gson.*;

import controller.Engine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import system.SystemParent;

import java.io.IOException;
import java.util.Map;


/**
 * Created by Willi on 9/26/2016.
 */
public class Weather extends SystemParent{
    private final String CURRENT_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/conditions/q/NY/Rochester.json";
    private final String WEEK_FORECAST_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/forecast/q/NY/Rochester.json";
    private final String HOURLY_FORECAST_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/hourly/q/NY/Rochester.json";
    private final  int updateInterval = 30 * 60000 ; //update once every 30 min
    private JsonObject conditions, weekForecast, hourForecast;
    String weahterToday;
    private HttpClient client ;

    public Weather(Engine e)
    {
        super(e,10*60*1000);
        client = HttpClientBuilder.create().build();
        update();
    }

    @Override
    public Object get(String what, Map<String, String> requestParams) {
        switch(what){
            case "current":
                return conditions;
            case "weekForecast":
                return weekForecast;
            case "hourlyForecast":
                return hourForecast;
            case "currentTemp":
                return getCurrentTemp();
            case "todayHigh":
                return getTodayHigh();
            case "todayLow":
                return getTodayLow();
            case "currentIcon":
                return getCurrentConditions();
            default:
                return "what not supported";
        }

    }

    @Override
    public String set(String what, String to, Map<String, String> requestParams) {
        return null;
    }



    private JsonObject queryCurrentWeather() throws IOException {
        HttpGet request = new HttpGet(CURRENT_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }

    private JsonObject queryHourForecastWeather() throws IOException {
        HttpGet request = new HttpGet(HOURLY_FORECAST_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);

        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }

    private JsonObject queryWeekForecastWeather() throws IOException {
        HttpGet request = new HttpGet(WEEK_FORECAST_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }



    @Scheduled(fixedRate = updateInterval)
    public void update() {
        try {
            conditions = queryCurrentWeather();
            //weekForecast = queryWeekForecastWeather();
            hourForecast = queryHourForecastWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getCurrentTemp() {
        return conditions.get("current_observation").getAsJsonObject().get("temp_c").getAsDouble();
    }

    private String getTodayForcast() {
        return weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    }

    private double getTodayHigh() {
        return Double.parseDouble(weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString());
    }

    private double getTodayLow() {
        return Double.parseDouble(weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("low").getAsJsonObject().get("celsius").getAsString());
    }


    private String getWeatherJson() {
        return "\"Weather\":" + "{\"currentTemp\":" + getCurrentTemp() + ",\"todayHigh\":" + getTodayHigh() + ",\"todayLow\":" + getTodayLow() + ",\"forecast\":\"" + getTodayForcast() + "\"}";
    }

    String getCurrentConditions(){
        String s =  conditions.get("current_observation").getAsJsonObject().get("icon_url").getAsString();
        return s.substring(s.lastIndexOf('/') + 1).replaceFirst("[.][^.]+$", "");
    }

    public static void main(String args[]){
        Weather weather = new Weather(new Engine());
        String s = null;
        JsonObject json = null;
        weather.update();
        System.out.println(weather.getWeatherJson());
        System.out.println(weather.getTodayForcast());
    }





}
