package controller;


import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import system.Weather.Weather;
import system.ClockDisplay.ClockDisplaySystem;
import system.SystemParent;
import system.coffee.Coffee;
import system.hue.HueSystem;
import system.hvac.HvacSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        systems.put("lights", new HueSystem(this));
        //systems.put("coffee", new Coffee(this));

        for(String id: systems.keySet()){
            (new Thread(systems.get(id))).start();
        }
    }


    public static String timestamp(){
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

    public void sendWSMessage(String s){
        for(WebSocketSession session : WebsocketHandler.sessions){
            try {
                session.sendMessage(new TextMessage(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
