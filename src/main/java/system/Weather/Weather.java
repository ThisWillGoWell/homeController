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
        super(e);
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
                return 7.0;
                //return getCurrentTemp();
            case "todayHigh":
                return getTodayHigh();
            case "todayLow":
                return getTodayLow();
            default:
                return "what not supported";
        }

    }

    @Override
    public String set(String what, String to, Map<String, String> requestParams) {
        return null;
    }

    @Override
    public String getStateJSON() {
        return null;
    }



    private JsonObject queryCurrentWeather() throws IOException {
        //HttpGet request = new HttpGet(CURRENT_WEATHER_ADDRESS);
        //ResponseHandler<String> responseHandler=new BasicResponseHandler();
        //String response = client.execute(request, responseHandler);
        JsonObject json = new JsonParser().parse("").getAsJsonObject();
        return json;
    }

    private JsonObject queryHourForecastWeather() throws IOException {
        //HttpGet request = new HttpGet(HOURLY_FORECAST_WEATHER_ADDRESS);
        //ResponseHandler<String> responseHandler=new BasicResponseHandler();
        //String response = client.execute(request, responseHandler);
        String response = "\n" +
                "{\n" +
                "  \"response\": {\n" +
                "  \"version\":\"0.1\",\n" +
                "  \"termsofService\":\"http://www.wunderground.com/weather/api/d/terms.html\",\n" +
                "  \"features\": {\n" +
                "  \"hourly\": 1\n" +
                "  }\n" +
                "\t}\n" +
                "\t\t,\n" +
                "\t\"hourly_forecast\": [\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"22\",\"hour_padded\": \"22\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"22\",\"mday_padded\": \"22\",\"yday\": \"295\",\"isdst\": \"1\",\"epoch\": \"1477188000\",\"pretty\": \"10:00 PM EDT on October 22, 2016\",\"civil\": \"10:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Saturday\",\"weekday_name_night\": \"Saturday Night\",\"weekday_name_abbrev\": \"Sat\",\"weekday_name_unlang\": \"Saturday\",\"weekday_name_night_unlang\": \"Saturday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"33\", \"metric\": \"1\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"93\",\n" +
                "\t\t\"wspd\": {\"english\": \"22\", \"metric\": \"35\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"297\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"63\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"0\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.74\", \"metric\": \"1007\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"23\",\"hour_padded\": \"23\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"22\",\"mday_padded\": \"22\",\"yday\": \"295\",\"isdst\": \"1\",\"epoch\": \"1477191600\",\"pretty\": \"11:00 PM EDT on October 22, 2016\",\"civil\": \"11:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Saturday\",\"weekday_name_night\": \"Saturday Night\",\"weekday_name_abbrev\": \"Sat\",\"weekday_name_unlang\": \"Saturday\",\"weekday_name_night_unlang\": \"Saturday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"33\", \"metric\": \"1\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"88\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"297\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"63\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"1\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.74\", \"metric\": \"1007\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"0\",\"hour_padded\": \"00\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477195200\",\"pretty\": \"12:00 AM EDT on October 23, 2016\",\"civil\": \"12:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"86\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"294\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"66\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"2\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.74\", \"metric\": \"1007\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"1\",\"hour_padded\": \"01\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477198800\",\"pretty\": \"1:00 AM EDT on October 23, 2016\",\"civil\": \"1:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"83\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"291\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"66\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"1\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.75\", \"metric\": \"1007\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"2\",\"hour_padded\": \"02\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477202400\",\"pretty\": \"2:00 AM EDT on October 23, 2016\",\"civil\": \"2:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"35\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"76\",\n" +
                "\t\t\"wspd\": {\"english\": \"20\", \"metric\": \"32\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"287\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"72\",\n" +
                "\t\t\"windchill\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"3\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.75\", \"metric\": \"1007\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"3\",\"hour_padded\": \"03\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477206000\",\"pretty\": \"3:00 AM EDT on October 23, 2016\",\"civil\": \"3:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"35\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"73\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"283\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"72\",\n" +
                "\t\t\"windchill\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"6\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.76\", \"metric\": \"1008\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"4\",\"hour_padded\": \"04\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477209600\",\"pretty\": \"4:00 AM EDT on October 23, 2016\",\"civil\": \"4:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"35\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"69\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"281\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"72\",\n" +
                "\t\t\"windchill\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"7\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.77\", \"metric\": \"1008\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"5\",\"hour_padded\": \"05\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477213200\",\"pretty\": \"5:00 AM EDT on October 23, 2016\",\"civil\": \"5:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"64\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"280\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"73\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"13\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.78\", \"metric\": \"1008\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"6\",\"hour_padded\": \"06\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477216800\",\"pretty\": \"6:00 AM EDT on October 23, 2016\",\"civil\": \"6:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"61\",\n" +
                "\t\t\"wspd\": {\"english\": \"21\", \"metric\": \"34\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"280\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"74\",\n" +
                "\t\t\"windchill\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"34\", \"metric\": \"1\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"14\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.8\", \"metric\": \"1009\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"7\",\"hour_padded\": \"07\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477220400\",\"pretty\": \"7:00 AM EDT on October 23, 2016\",\"civil\": \"7:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"58\",\n" +
                "\t\t\"wspd\": {\"english\": \"20\", \"metric\": \"32\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"279\"},\n" +
                "\t\t\"wx\": \"Partly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"73\",\n" +
                "\t\t\"windchill\": {\"english\": \"37\", \"metric\": \"3\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"37\", \"metric\": \"3\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"8\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.82\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"8\",\"hour_padded\": \"08\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477224000\",\"pretty\": \"8:00 AM EDT on October 23, 2016\",\"civil\": \"8:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"38\",\n" +
                "\t\t\"wspd\": {\"english\": \"20\", \"metric\": \"32\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"280\"},\n" +
                "\t\t\"wx\": \"Partly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"74\",\n" +
                "\t\t\"windchill\": {\"english\": \"37\", \"metric\": \"3\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"37\", \"metric\": \"3\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"3\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"9\",\"hour_padded\": \"09\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477227600\",\"pretty\": \"9:00 AM EDT on October 23, 2016\",\"civil\": \"9:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"38\", \"metric\": \"3\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"63\",\n" +
                "\t\t\"wspd\": {\"english\": \"20\", \"metric\": \"32\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"282\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"73\",\n" +
                "\t\t\"windchill\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.85\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"10\",\"hour_padded\": \"10\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477231200\",\"pretty\": \"10:00 AM EDT on October 23, 2016\",\"civil\": \"10:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"49\", \"metric\": \"9\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"62\",\n" +
                "\t\t\"wspd\": {\"english\": \"22\", \"metric\": \"35\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"283\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"1\",\n" +
                "\t\t\"humidity\": \"68\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"49\", \"metric\": \"9\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.86\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"11\",\"hour_padded\": \"11\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477234800\",\"pretty\": \"11:00 AM EDT on October 23, 2016\",\"civil\": \"11:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"51\", \"metric\": \"11\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"40\", \"metric\": \"4\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"68\",\n" +
                "\t\t\"wspd\": {\"english\": \"23\", \"metric\": \"37\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"286\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"2\",\n" +
                "\t\t\"humidity\": \"66\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"51\", \"metric\": \"11\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.86\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"12\",\"hour_padded\": \"12\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477238400\",\"pretty\": \"12:00 PM EDT on October 23, 2016\",\"civil\": \"12:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"41\", \"metric\": \"5\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"73\",\n" +
                "\t\t\"wspd\": {\"english\": \"23\", \"metric\": \"37\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"285\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"2\",\n" +
                "\t\t\"humidity\": \"65\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.85\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"13\",\"hour_padded\": \"13\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477242000\",\"pretty\": \"1:00 PM EDT on October 23, 2016\",\"civil\": \"1:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"54\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"83\",\n" +
                "\t\t\"wspd\": {\"english\": \"23\", \"metric\": \"37\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"282\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"2\",\n" +
                "\t\t\"humidity\": \"64\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"54\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.84\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"14\",\"hour_padded\": \"14\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477245600\",\"pretty\": \"2:00 PM EDT on October 23, 2016\",\"civil\": \"2:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"55\", \"metric\": \"13\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"87\",\n" +
                "\t\t\"wspd\": {\"english\": \"22\", \"metric\": \"35\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"279\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"2\",\n" +
                "\t\t\"humidity\": \"61\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"55\", \"metric\": \"13\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"15\",\"hour_padded\": \"15\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477249200\",\"pretty\": \"3:00 PM EDT on October 23, 2016\",\"civil\": \"3:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"56\", \"metric\": \"13\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Partly Cloudy\",\n" +
                "\t\t\"icon\": \"partlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/partlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"2\",\n" +
                "\t\t\"sky\": \"89\",\n" +
                "\t\t\"wspd\": {\"english\": \"20\", \"metric\": \"32\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"272\"},\n" +
                "\t\t\"wx\": \"Cloudy/Wind\",\n" +
                "\t\t\"uvi\": \"1\",\n" +
                "\t\t\"humidity\": \"60\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"56\", \"metric\": \"13\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"16\",\"hour_padded\": \"16\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477252800\",\"pretty\": \"4:00 PM EDT on October 23, 2016\",\"civil\": \"4:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"57\", \"metric\": \"14\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Overcast\",\n" +
                "\t\t\"icon\": \"cloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/cloudy.gif\",\n" +
                "\t\t\"fctcode\": \"4\",\n" +
                "\t\t\"sky\": \"90\",\n" +
                "\t\t\"wspd\": {\"english\": \"18\", \"metric\": \"29\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"264\"},\n" +
                "\t\t\"wx\": \"Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"58\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"57\", \"metric\": \"14\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"15\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.82\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"17\",\"hour_padded\": \"17\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477256400\",\"pretty\": \"5:00 PM EDT on October 23, 2016\",\"civil\": \"5:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"57\", \"metric\": \"14\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Overcast\",\n" +
                "\t\t\"icon\": \"cloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/cloudy.gif\",\n" +
                "\t\t\"fctcode\": \"4\",\n" +
                "\t\t\"sky\": \"91\",\n" +
                "\t\t\"wspd\": {\"english\": \"17\", \"metric\": \"27\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WSW\", \"degrees\": \"254\"},\n" +
                "\t\t\"wx\": \"Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"61\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"57\", \"metric\": \"14\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"20\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.82\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"18\",\"hour_padded\": \"18\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477260000\",\"pretty\": \"6:00 PM EDT on October 23, 2016\",\"civil\": \"6:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"56\", \"metric\": \"13\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"79\",\n" +
                "\t\t\"wspd\": {\"english\": \"16\", \"metric\": \"26\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WSW\", \"degrees\": \"253\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"65\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"56\", \"metric\": \"13\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"21\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.82\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"19\",\"hour_padded\": \"19\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477263600\",\"pretty\": \"7:00 PM EDT on October 23, 2016\",\"civil\": \"7:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"54\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"61\",\n" +
                "\t\t\"wspd\": {\"english\": \"13\", \"metric\": \"21\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WSW\", \"degrees\": \"245\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"71\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"54\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"23\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"20\",\"hour_padded\": \"20\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477267200\",\"pretty\": \"8:00 PM EDT on October 23, 2016\",\"civil\": \"8:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"67\",\n" +
                "\t\t\"wspd\": {\"english\": \"9\", \"metric\": \"14\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WSW\", \"degrees\": \"243\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"75\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"5\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"21\",\"hour_padded\": \"21\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477270800\",\"pretty\": \"9:00 PM EDT on October 23, 2016\",\"civil\": \"9:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"69\",\n" +
                "\t\t\"wspd\": {\"english\": \"9\", \"metric\": \"14\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WSW\", \"degrees\": \"259\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"77\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"5\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"22\",\"hour_padded\": \"22\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477274400\",\"pretty\": \"10:00 PM EDT on October 23, 2016\",\"civil\": \"10:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"73\",\n" +
                "\t\t\"wspd\": {\"english\": \"8\", \"metric\": \"13\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"W\", \"degrees\": \"272\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"78\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"53\", \"metric\": \"12\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"7\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"23\",\"hour_padded\": \"23\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"23\",\"mday_padded\": \"23\",\"yday\": \"296\",\"isdst\": \"1\",\"epoch\": \"1477278000\",\"pretty\": \"11:00 PM EDT on October 23, 2016\",\"civil\": \"11:00 PM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Sunday\",\"weekday_name_night\": \"Sunday Night\",\"weekday_name_abbrev\": \"Sun\",\"weekday_name_unlang\": \"Sunday\",\"weekday_name_night_unlang\": \"Sunday Night\",\"ampm\": \"PM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"52\", \"metric\": \"11\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"72\",\n" +
                "\t\t\"wspd\": {\"english\": \"7\", \"metric\": \"11\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"285\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"81\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"52\", \"metric\": \"11\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"13\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"0\",\"hour_padded\": \"00\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477281600\",\"pretty\": \"12:00 AM EDT on October 24, 2016\",\"civil\": \"12:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"51\", \"metric\": \"11\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"70\",\n" +
                "\t\t\"wspd\": {\"english\": \"8\", \"metric\": \"13\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"303\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"82\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"51\", \"metric\": \"11\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"14\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"1\",\"hour_padded\": \"01\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477285200\",\"pretty\": \"1:00 AM EDT on October 24, 2016\",\"civil\": \"1:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"50\", \"metric\": \"10\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"62\",\n" +
                "\t\t\"wspd\": {\"english\": \"7\", \"metric\": \"11\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"303\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"83\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"50\", \"metric\": \"10\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"22\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"2\",\"hour_padded\": \"02\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477288800\",\"pretty\": \"2:00 AM EDT on October 24, 2016\",\"civil\": \"2:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"49\", \"metric\": \"9\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"71\",\n" +
                "\t\t\"wspd\": {\"english\": \"7\", \"metric\": \"11\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"NW\", \"degrees\": \"305\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"84\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"49\", \"metric\": \"9\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"13\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.83\", \"metric\": \"1010\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"3\",\"hour_padded\": \"03\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477292400\",\"pretty\": \"3:00 AM EDT on October 24, 2016\",\"civil\": \"3:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"48\", \"metric\": \"9\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"74\",\n" +
                "\t\t\"wspd\": {\"english\": \"9\", \"metric\": \"14\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"300\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"83\",\n" +
                "\t\t\"windchill\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"48\", \"metric\": \"9\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"16\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.85\", \"metric\": \"1011\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"4\",\"hour_padded\": \"04\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477296000\",\"pretty\": \"4:00 AM EDT on October 24, 2016\",\"civil\": \"4:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"47\", \"metric\": \"8\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"70\",\n" +
                "\t\t\"wspd\": {\"english\": \"9\", \"metric\": \"14\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"303\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"85\",\n" +
                "\t\t\"windchill\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"18\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.88\", \"metric\": \"1012\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"5\",\"hour_padded\": \"05\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477299600\",\"pretty\": \"5:00 AM EDT on October 24, 2016\",\"civil\": \"5:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"46\", \"metric\": \"8\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"42\", \"metric\": \"6\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"67\",\n" +
                "\t\t\"wspd\": {\"english\": \"10\", \"metric\": \"16\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"302\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"84\",\n" +
                "\t\t\"windchill\": {\"english\": \"42\", \"metric\": \"5\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"42\", \"metric\": \"5\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"8\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.9\", \"metric\": \"1013\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"6\",\"hour_padded\": \"06\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477303200\",\"pretty\": \"6:00 AM EDT on October 24, 2016\",\"civil\": \"6:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"41\", \"metric\": \"5\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"70\",\n" +
                "\t\t\"wspd\": {\"english\": \"10\", \"metric\": \"16\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"302\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"83\",\n" +
                "\t\t\"windchill\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"5\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.94\", \"metric\": \"1014\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"7\",\"hour_padded\": \"07\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477306800\",\"pretty\": \"7:00 AM EDT on October 24, 2016\",\"civil\": \"7:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"44\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/nt_mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"69\",\n" +
                "\t\t\"wspd\": {\"english\": \"10\", \"metric\": \"16\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"304\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"82\",\n" +
                "\t\t\"windchill\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"39\", \"metric\": \"4\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"4\",\n" +
                "\t\t\"mslp\": {\"english\": \"29.97\", \"metric\": \"1015\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"8\",\"hour_padded\": \"08\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477310400\",\"pretty\": \"8:00 AM EDT on October 24, 2016\",\"civil\": \"8:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"43\", \"metric\": \"6\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"38\", \"metric\": \"3\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"67\",\n" +
                "\t\t\"wspd\": {\"english\": \"12\", \"metric\": \"19\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"NW\", \"degrees\": \"304\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"80\",\n" +
                "\t\t\"windchill\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"36\", \"metric\": \"2\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"6\",\n" +
                "\t\t\"mslp\": {\"english\": \"30.0\", \"metric\": \"1016\"}\n" +
                "\t\t}\n" +
                "\t\t,\n" +
                "\t\t{\n" +
                "\t\t\"FCTTIME\": {\n" +
                "\t\t\"hour\": \"9\",\"hour_padded\": \"09\",\"min\": \"00\",\"min_unpadded\": \"0\",\"sec\": \"0\",\"year\": \"2016\",\"mon\": \"10\",\"mon_padded\": \"10\",\"mon_abbrev\": \"Oct\",\"mday\": \"24\",\"mday_padded\": \"24\",\"yday\": \"297\",\"isdst\": \"1\",\"epoch\": \"1477314000\",\"pretty\": \"9:00 AM EDT on October 24, 2016\",\"civil\": \"9:00 AM\",\"month_name\": \"October\",\"month_name_abbrev\": \"Oct\",\"weekday_name\": \"Monday\",\"weekday_name_night\": \"Monday Night\",\"weekday_name_abbrev\": \"Mon\",\"weekday_name_unlang\": \"Monday\",\"weekday_name_night_unlang\": \"Monday Night\",\"ampm\": \"AM\",\"tz\": \"\",\"age\": \"\",\"UTCDATE\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"temp\": {\"english\": \"45\", \"metric\": \"7\"},\n" +
                "\t\t\"dewpoint\": {\"english\": \"37\", \"metric\": \"3\"},\n" +
                "\t\t\"condition\": \"Mostly Cloudy\",\n" +
                "\t\t\"icon\": \"mostlycloudy\",\n" +
                "\t\t\"icon_url\":\"http://icons.wxug.com/i/c/k/mostlycloudy.gif\",\n" +
                "\t\t\"fctcode\": \"3\",\n" +
                "\t\t\"sky\": \"78\",\n" +
                "\t\t\"wspd\": {\"english\": \"13\", \"metric\": \"21\"},\n" +
                "\t\t\"wdir\": {\"dir\": \"WNW\", \"degrees\": \"298\"},\n" +
                "\t\t\"wx\": \"Mostly Cloudy\",\n" +
                "\t\t\"uvi\": \"0\",\n" +
                "\t\t\"humidity\": \"74\",\n" +
                "\t\t\"windchill\": {\"english\": \"38\", \"metric\": \"4\"},\n" +
                "\t\t\"heatindex\": {\"english\": \"-9999\", \"metric\": \"-9999\"},\n" +
                "\t\t\"feelslike\": {\"english\": \"38\", \"metric\": \"4\"},\n" +
                "\t\t\"qpf\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"snow\": {\"english\": \"0.0\", \"metric\": \"0\"},\n" +
                "\t\t\"pop\": \"6\",\n" +
                "\t\t\"mslp\": {\"english\": \"30.03\", \"metric\": \"1017\"}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}\n";
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
    public void update()
    {
        try {
           // conditions = queryCurrentWeather();
            //weekForecast = queryWeekForecastWeather();
            hourForecast = queryHourForecastWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getCurrentTemp()
    {
        return conditions.get("current_observation").getAsJsonObject().get("temp_c").getAsDouble();
    }

    private String getTodayForcast()
    {
        return weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
    }

    private double getTodayHigh()
    {
        return Double.parseDouble(weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("high").getAsJsonObject().get("celsius").getAsString());
    }

    private double getTodayLow()
    {
        return Double.parseDouble(weekForecast.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray().get(0).getAsJsonObject().get("low").getAsJsonObject().get("celsius").getAsString());
    }


    private String getWeatherJson()
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
