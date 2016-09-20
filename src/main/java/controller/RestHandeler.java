package controller;

/**
 * Created by Will on 9/3/2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RestHandeler {

    Engine engine;
    @Autowired
    public RestHandeler(Engine e)
    {
        engine = e;
    }
    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
    public void init(){

        System.out.println("Init");
        engine.initialize();
    }


    @RequestMapping(value = "/setRoomTemp", method = RequestMethod.GET)
    public void setRoomTemp(@RequestParam(value = "temp", defaultValue = "27") String temp)
    {
        System.out.println("Setting Room Temp: " + temp);
        try {
            engine.setRoomTemp(Double.parseDouble(temp));
        }
        catch (Exception e)
        {
            System.out.println("Error in setting Room Temp");
        }
    }

    @RequestMapping(value = "/setSystemTemp", method = RequestMethod.GET)
    public void setTemp(@RequestParam(value="temp", defaultValue = "27") String temp)
    {

        try {
            engine.setSystemTemp(Double.parseDouble(temp));
        }
        catch(Exception e)
        {
            System.out.println("Error in setting temp");
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/setAc")
    public void setAc(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        if(state == "off")
            engine.setAc(false);
        else if(state == "on")
            engine.setAc(true);
    }

    @RequestMapping(value = "/setHeat")
    public void setHeat(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        if(state == "off")
            engine.setHeat(false);
        else if(state == "on")
            engine.setHeat(true);
    }

    @RequestMapping(value = "/setFan")
    public void setFan(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        if(state == "off")
            engine.setFan(false);
        else if(state == "on")
            engine.setFan(true);
    }



    @RequestMapping(value = "/setPower")
    public void setPower(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        if(state == "off")
        {
            engine.setPower(false);
        }
        else if(state == "on")
        {
            engine.setPower(true);
        }

    }

    @RequestMapping(value = "/getState")
    public String getState()
    {
        return engine.getState();
    }



}
