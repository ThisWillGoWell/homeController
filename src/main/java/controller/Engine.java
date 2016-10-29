package controller;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import system.Weather.Weather;
import system.ClockDisplay.ClockDisplaySystem;
import system.SystemParent;
import system.hvac.HvacSystem;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
public class Engine {
    private HashMap<String, SystemParent> systems;

    public Engine()
    {
        initialize();
    }
    private void initialize()
    {
        systems = new HashMap<>();
        systems.put("weather", new Weather(this));
        systems.put("HVAC", new HvacSystem(this));
        systems.put("clock", new ClockDisplaySystem(this));

        for(String id: systems.keySet())
        {
            systems.get(id).setLastUpdateTime(System.currentTimeMillis());
        }
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

    public SystemParent getSystem(String s)
    {
        return systems.get(s);
    }



    public void update(){
        long t = System.currentTimeMillis();
        {
            for (String id: systems.keySet() ) {
                SystemParent s = systems.get(id);
                if(s.getLastUpdateTime() + s.getUpdateInterval() <= t) {
                    s.setLastUpdateTime(t);
                    s.update();
                }
            }
        }
    }

    @Scheduled(initialDelay = 10,fixedRate = 3000)
    public void updateCurrentTemp() {
        systems.get("HVAC").update();
    }

    @Scheduled(initialDelay = 2,fixedRate = 4050)
    public void updateClock(){
        systems.get("clock").update();
    }
}
