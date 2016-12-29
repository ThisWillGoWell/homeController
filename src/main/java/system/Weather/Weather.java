package system.Weather;

import com.google.gson.*;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import controller.Engine;
import controller.Parcel;
import controller.ParcelException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import system.SystemParent;

import java.io.IOException;


/**
 * Created by Willi on 9/26/2016.
 * Class that manages the WeatherUnderground API Class
 * as a system.
 *
 */
public class Weather extends SystemParent{
    private final String CURRENT_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/conditions/q/NY/Rochester.json";
    private final String WEEK_FORECAST_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/forecast/q/NY/Rochester.json";
    private final String HOURLY_FORECAST_WEATHER_ADDRESS = "http://api.wunderground.com/api/0457bdf6163baa58/hourly/q/NY/Rochester.json";
    private final  int updateInterval = 30 * 60000 ; //update once every 30 min
    private Parcel conditions, weekForecast, hourForecast;
    String weahterToday;
    private HttpClient client ;

    /*
    Update once every 10 min
    Use HTTPClientBulder class for http calls
     */
    public Weather(Engine e)
    {
        super(e,10*60*1000);
        client = HttpClientBuilder.create().build();
        update();
    }

    @Override
    public Parcel process(Parcel p) {
        try {
            switch(p.getString("op")){
                case "get":
                    return get(p);
                default:
                    throw ParcelException.OP_NOT_SUPPORTED(p);
            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }
    }

    /*

     */
    public Parcel get(Parcel p) throws ParcelException {
        switch(p.getString("what")){
            case "current":
                return Parcel.RESPONSE_PARCEL(conditions);
            case "weekForecast":
                return Parcel.RESPONSE_PARCEL(weekForecast);
            case "hourlyForecast":
                return Parcel.RESPONSE_PARCEL(hourForecast);
            case "currentTemp":
                return Parcel.RESPONSE_PARCEL(getCurrentTemp());
            case "todayHigh":
                return Parcel.RESPONSE_PARCEL(getTodayHigh());
            case "todayLow":
                return Parcel.RESPONSE_PARCEL(getTodayLow());
            case "currentIcon":
                return Parcel.RESPONSE_PARCEL(getCurrentConditions());
            default:
                throw ParcelException.WHAT_NOT_SUPPORTED(p);
        }

    }


    /*
    Query the current weather URL of the weather underground API
     */
    private Parcel queryCurrentWeather() throws IOException {
        HttpGet request = new HttpGet(CURRENT_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        return Parcel.PROCESS_JSONSTR(response);
    }

    /*
    Query the hour-by-hour forecast
     */
    private Parcel queryHourForecastWeather() throws IOException {
        HttpGet request = new HttpGet(HOURLY_FORECAST_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        return Parcel.PROCESS_JSONSTR(response);
    }

    /*
    Query the Whole weeks weather, day by day
     */
    private Parcel queryWeekForecastWeather() throws IOException {
        HttpGet request = new HttpGet(WEEK_FORECAST_WEATHER_ADDRESS);
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(request, responseHandler);
        return Parcel.PROCESS_JSONSTR(response);

    }


    /*
        On update will update the response jsons
     */
    public void update() {
        try {
            conditions = queryCurrentWeather();
            //weekForecast = queryWeekForecastWeather();
            hourForecast = queryHourForecastWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    The following methods just parse the reponse json
     */
    private double getCurrentTemp() throws ParcelException {
        return conditions.getParcel("current_observation").getDouble("temp_c");

    }

    private String getTodayForecast() throws ParcelException{
        return weekForecast.getParcel("forecast").getParcel("simpleforecast").getParcelArray("forecastday").getParcel(0).getString("icon");
    }

    private double getTodayHigh() throws ParcelException{
        return weekForecast.getParcel("forecast").getParcel("simpleforecast").getParcelArray("forecastday").getParcel(0).getParcel("high").getDouble("celsius");
    }

    private double getTodayLow() throws ParcelException{
        return weekForecast.getParcel("forecast").getParcel("simpleforecast").getParcelArray("forecastday").getParcel(0).getParcel("low").getDouble("celsius");
    }


    String getCurrentConditions() throws ParcelException{
        String s =  conditions.getParcel("current_observation").getString("icon_url");
        return s.substring(s.lastIndexOf('/') + 1).replaceFirst("[.][^.]+$", "");
    }


    public static void main(String args[]){
        Weather weather = new Weather(new Engine());
        String s = null;
        JsonObject json = null;
        weather.update();
    }





}
