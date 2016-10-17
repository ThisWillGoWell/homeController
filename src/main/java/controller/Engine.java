package controller;

import system.Weather.Weather;
import system.ClockDisplay.ClockDisplaySystem;
import system.SystemParent;
import org.springframework.stereotype.Service;
import system.hvac.HvacSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
@Service
public class Engine {
    HashMap<String, SystemParent> systems;

    public Engine()
    {
        initialize();
    }
    void initialize()
    {
        systems = new HashMap<>();
        systems.put("clock", new ClockDisplaySystem(this));
        systems.put("HVAC", new HvacSystem(this));
        //systems.put("weather", new Weather(this));
    }


    public static String timestamp()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\t").format(Calendar.getInstance().getTime());
    }
    public static String time()
    {
        return new SimpleDateFormat("h:mm").format(Calendar.getInstance().getTime());
    }
    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public Object get(String system, String what, Map<String, String> requestParams)
    {
        if(systems.keySet().contains(system)) {
            return systems.get(system).get(what, requestParams);
        }
        return "system not found";
    }
    public Object get(Map<String, String> requestParams)
    {
        if (!(requestParams.containsKey("system") && requestParams.containsKey("what")))
        {
            return "missing required params: system, what";
        }
        String system = requestParams.get("system");
        String what = requestParams.get("what");


        if(systems.keySet().contains(system)) {
            return systems.get(system).get(what, requestParams);
        }
        return "system not found";

    }

    public String set(Map<String, String> requestParams)
    {
        if (!(requestParams.containsKey("system") && requestParams.containsKey("to") && requestParams.containsKey("what")))
        {
            return "missing required params: system, to, what";
        }
        String system = requestParams.get("system");
        String what = requestParams.get("what");
        String to = requestParams.get("to");

        if(systems.keySet().contains(system))
        {
            return systems.get(system).set(what, to, requestParams);
        }
        return "system not found";
    }
    /*
    @Scheduled(fixedRate = 5000)
    public void updateSystem()
    {
        systemHVAC.update();
    }

    @Scheduled(fixedRate = 5 * 60000)
    public void updateCurrentTemp()
    {
        weather.update();
    }
    public File getImageResource()
    {
        return clockDisplaySystem.getResouceGif();
    }



    double getMaxTempToday()
    {
        return weather.getTodayHigh();
    }

    double getMinTempToday()
    {
        return weather.getTodayLow();
    }

    String getForcast()
    {
        return weather.getTodayForcast();
    }

    String getImageTime(long start, long stop){return clockDisplaySystem.getImageUpdate(start, stop);}

    String getState()
    {
        return "{\"state\":{"  + systemHVAC.getStateJSON() + "}}";
    }
    */






}
