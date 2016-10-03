package controller;

/**
 * Created by Will on 9/3/2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class RestHandeler {

    @RequestMapping(value = "/")
    public String index()
    {
        return "Home Controller";

    }


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
    public String setRoomTemp(@RequestParam(value = "temp", defaultValue = "27") String temp)
    {
        try {
            engine.setRoomTemp(Double.parseDouble(temp));
        }
        catch (Exception e)
        {
            System.out.println("Error in setting Room Temp");
        }
        return engine.getState();
    }

    @RequestMapping(value = "/setSystemTemp", method = RequestMethod.GET)
    public String setTemp(@RequestParam(value="temp", defaultValue = "27") String temp)
    {
        System.out.println("Setting System Temp");
        try {
            engine.setSystemTemp(Double.parseDouble(temp));
        }
        catch(Exception e)
        {
            System.out.println("Error in setting temp");
            e.printStackTrace();
        }
        return engine.getState();
    }

    @RequestMapping(value = "/setAc")
    public String setAc(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        System.out.println("Setting AC to " + state);
        if(state.equals("off"))
            engine.setAc(false);
        else if(state.equals("on"))
            engine.setAc(true);
        return engine.getState();
    }

    @RequestMapping(value = "/setHeat")
    public String setHeat(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        System.out.println("Settign Heat to " + state);
        if(state.equals("off"))
            engine.setHeat(false);
        else if(state.equals("on"))
            engine.setHeat(true);

        return engine.getState();
    }

    @RequestMapping(value = "/setFan")
    public String setFan(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        if(state.equals("off"))
            engine.setFan(false);
        else if(state.equals("on"))
            engine.setFan(true);
        return engine.getState();
    }



    @RequestMapping(value = "/setPower")
    public String setPower(@RequestParam(value = "state", defaultValue = "off") String state)
    {
        System.out.println("Setting Power " + state);
        if(state.equals("off"))
        {
            engine.setPower(false);
        }
        else if(state.equals("on"))
        {
            engine.setPower(true);
        }
        return engine.getState();
    }

    @RequestMapping(value = "/getState")
    public String getState()
    {
        return engine.getState();
    }

    @RequestMapping(value = "/getImageTimeline")
    public String getImageTimeline(@RequestParam(value = "start") String start, @RequestParam(value = "end") String end)
    {
        long startTime = System.currentTimeMillis();
        String s= engine.getImageTime(Long.parseLong(start), Long.parseLong(end));
        System.out.println(System.currentTimeMillis() - startTime);
        return s;
    }

    @RequestMapping(value= "/getImageResource", method = RequestMethod.GET, produces = "application/gif")
    public ResponseEntity<InputStreamResource> getImageResource() throws IOException
    {
        File image = engine.getImageResource();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");


        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(image.length())
                .contentType(MediaType.IMAGE_GIF)
                .body(new InputStreamResource(new FileInputStream(image)));

    }


}
