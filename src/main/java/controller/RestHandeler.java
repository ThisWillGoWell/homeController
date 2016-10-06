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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(@RequestParam(value = "system") String system, @RequestParam(value = "what") String what)
    {
        return engine.get(system,what);
    }

    @RequestMapping(value = "/set", method = RequestMethod.PUT)
    public String set(@RequestParam(value = "system") String system, @RequestParam(value = "what") String what, @RequestParam(value = "to") String to)
    {
        return engine.set(system,what,to);
    }


}
