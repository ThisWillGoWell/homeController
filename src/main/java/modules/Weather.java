package modules;

import com.google.gson.*;

import controller.Engine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import system.SystemParent;

import java.io.IOException;


/**
 * Created by Willi on 9/26/2016.
 */
public class Weather extends SystemParent{
    private final String CURRENT_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/conditions/q/NY/Rochester.json";
    private final String FORCAST_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/forecast/q/NY/Rochester.json";


    private HttpClient client ;

    public Weather(Engine e)
    {
        super(e);
        client = HttpClientBuilder.create().build();
        update();
    }

    @Override
    public Object get(String what) {

        return null;
    }

    @Override
    public String set(String what, String to) {
        return null;
    }

    @Override
    public String getStateJSON() {
        return null;
    }

    private JsonObject conditions, forecast;
    String weahterToday;

    private JsonObject queryCurrentWeather() throws IOException {
        HttpGet request = new HttpGet(CURRENT_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }

    private JsonObject queryForecastWeather() throws IOException {
        HttpGet request = new HttpGet(FORCAST_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }



    public void update()
    {

        try {
            conditions = queryCurrentWeather();
            forecast = queryForecastWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public double getCurrentTemp()
    {
        return conditions.get("current_observation").getAsJsonObject().get("temp_c").getAsDouble();
    }

    public String getTodayForcast()
    {
        return forecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    }

    public double getTodayHigh()
    {
        return Double.parseDouble(forecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString());
    }

    public double getTodayLow()
    {
        return Double.parseDouble(forecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("low").getAsJsonObject().get("celsius").getAsString());
    }


    public String getWeatherJson()
    {
        return "\"Weather\":" + "{\"currentTemp\":" + getCurrentTemp() + ",\"todayHigh\":" + getTodayHigh() + ",\"todayLow\":" + getTodayLow() + ",\"forecast\":\"" + getTodayForcast() + "\"}";


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
