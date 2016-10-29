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


    private Engine engine;






}


