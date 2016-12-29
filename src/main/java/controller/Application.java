package controller;

import com.google.gson.JsonObject;
import org.json.hue.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableWebSocket
@RestController
public class Application extends SpringBootServletInitializer implements WebSocketConfigurer {

    private static Engine e = new Engine();

    static Engine getEngine() {
        return e;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    org.springframework.web.socket.WebSocketHandler getHandler() {
        return new PerConnectionWebSocketHandler(WebsocketHandler.class);
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getHandler(), "/ws").setAllowedOrigins("*");
    }


    @RequestMapping(value = "/c", method = RequestMethod.POST)
    public Object command(@RequestBody String jsonString) {
        Parcel p = e.command(Parcel.PROCESS_JSONSTR(jsonString));
        try {
            return p.toPayload();
        } catch (ParcelException e1) {
            return e1.toString();
        }
    }
}