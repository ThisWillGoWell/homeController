package controller;

/**
 * Created by Will on 9/3/2016.
 */
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
        System.out.println("FUCKK");
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
        return engine.get(allRequestParams);
    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(@RequestParam Map<String,String> allRequestParams, ModelMap model)
    {
        return engine.set(allRequestParams);
    }




}
