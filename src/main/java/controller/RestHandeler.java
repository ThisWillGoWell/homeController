package controller;

/**
 * Created by Will on 9/3/2016.
 */
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class RestHandeler {

    @RequestMapping(value = "/**",method = RequestMethod.OPTIONS)
    public String getOption(HttpServletResponse response,Model model)
    {
        response.setHeader("Access-Control-Allow-Origin","*");

        response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");

        return "";
    }


    Engine engine;
    @Autowired
    public RestHandeler(Engine e)
    {
        engine = e;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        Object o =  engine.get(allRequestParams);
        if(o.getClass() == JsonObject.class)
        {
            return o.toString();
        }
        return o;
    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(@RequestParam Map<String,String> allRequestParams, ModelMap model)
    {
        return engine.set(allRequestParams);
    }




}
