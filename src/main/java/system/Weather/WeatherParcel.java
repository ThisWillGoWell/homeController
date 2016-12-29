package system.Weather;

import controller.Parcel;

/**
 * Created by Willi on 12/27/2016.
 */
public class WeatherParcel {
    public static Parcel GET_TODAY_HIGH(){
        return Parcel.GET_PARCEL("weather","todayHigh");
    }

    public static Parcel GET_CURRENT(){
        return Parcel.GET_PARCEL("weather","weekForecast");
    }

    public static Parcel GET_HOUR_FORECAST(){
        return Parcel.GET_PARCEL("weather","hourlyForecast");
    }

    public static Parcel GET_CURRENT_TEMP(){
        return Parcel.GET_PARCEL("weather","currentTemp");
    }
    public static Parcel GET_TODAY_LOW(){
        return Parcel.GET_PARCEL("weather","todayLow");
    }

    public static Parcel GET_CURRENT_ICON(){
        return Parcel.GET_PARCEL("weather","currentIcon");
    }

}
