package controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import parcel.Parcel;
import parcel.SystemException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
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
public class Application extends SpringBootServletInitializer{

    private static Engine e = new Engine();

    public static Engine getEngine() {
        return e;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/c", method = RequestMethod.POST)
    public Object command(@RequestBody String jsonString) {
        Parcel p = e.command(Parcel.PROCESS_JSONSTR(jsonString));
        try {
            String s = (String) p.toPayload();
            return p.toPayload();
        } catch (SystemException e1) {
            return Parcel.RESPONSE_PARCEL_ERROR(e1);
        }
    }

    @RequestMapping(value = "/spotifyRD", method = RequestMethod.GET)
    public Object command(@RequestParam Map<String,String> allRequestParams, ModelMap model ) {
        Parcel requset = null;
        try {
            requset = Parcel.SET_PARCEL("spotify","userCode",Parcel.PROCESS_MAP(allRequestParams).getString("code"));
        } catch (SystemException e1) {
            e1.printStackTrace();
        }
        Parcel p = e.command(requset);
        if(p.success()){
            e.command(Parcel.OP_PARCEL("login"));
        }
        try {
            String s = (String) p.toPayload();
            return p.toPayload();
        } catch (SystemException e1) {
            return Parcel.RESPONSE_PARCEL_ERROR(e1);
        }
    }

}