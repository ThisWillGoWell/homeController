package controller;

import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

import java.util.Map;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableWebSocket
@RestController
public class Application extends SpringBootServletInitializer implements WebSocketConfigurer{

    private static Engine e = new Engine();
    static Engine getEngine(){return e;}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    org.springframework.web.socket.WebSocketHandler getHandler()
    {
        return new PerConnectionWebSocketHandler(WebsocketHandler.class);
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getHandler(), "/ws").setAllowedOrigins("*");
    }




    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object get(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        Object o =  e.get(allRequestParams);
        if(o != null & o.getClass() == JsonObject.class)
        {
            return o.toString();
        }
        return o;
    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        return e.set(allRequestParams);
    }

}
