package controller;


import parcel.Parcel;
import parcel.SystemException;
import system.SystemParent;

import system.clockDisplay.ClockDisplaySystem;
import system.hue.HueSystem;
import system.hvac.HvacSystem;
import system.weather.Weather;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
public class Engine {
    private HashMap<String, SystemParent> systems;

    HashMap<String, SystemParent> getSystems(){
        return systems;
    }
    public Engine() {
        initialize();
    }


    private void initialize() {
        systems = new HashMap<>();
        systems.put("weather", new Weather(this));
        systems.put("HVAC", new HvacSystem(this));
        //systems.put("clock", new ClockDisplaySystem(this));
        systems.put("lights", new HueSystem(this));
        //systems.put("coffee", new Coffee(this));
        //systems.put("spotify", new Spotify(this));
        for (String id : systems.keySet()) {
            (new Thread(systems.get(id))).start();
        }
    }


    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\t").format(Calendar.getInstance().getTime());
    }

    public static String time() {
        return new SimpleDateFormat("h:mm").format(Calendar.getInstance().getTime());
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public Parcel command(Parcel p){
        try {
            Parcel response = systems.get(p.getString("system")).command(p);
            return response;
        } catch (SystemException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        } catch (NullPointerException e){
            return Parcel.RESPONSE_PARCEL_ERROR(SystemException.SYSTEM_NOT_FOUND_EXCEPTION(e,p));
        }
    }
}
