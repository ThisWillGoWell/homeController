package controller.get;

/**
 * Created by Willi on 10/24/2016.
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import controller.Engine;
import org.json.hue.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
@EnableWebSocket
public class WebsocketHandeler  implements WebSocketConfigurer{

    Engine e;
    @Autowired
    public WebsocketHandeler(Engine engine)
    {
        e = engine;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new Handler(e), "/ws");
    }

    private class Handler extends TextWebSocketHandler{
        Engine e;
        Handler(Engine e){
            this.e = e;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            super.handleTextMessage(session, message);
            //pase message into Json

            JSONObject m = new JSONObject(message.toString());
            Map<String,String> params = new HashMap<>();
            Object resp = null;
            String reply;
            for(Object key : m.keySet()){
                String k = (String) key;
                params.put(k,m.getString(k));
            }
            if(Objects.equals(params.get("op"), "get")){
                resp =  e.get(params);
            }
            else if(Objects.equals(params.get("op"), "get")){
                resp =  e.set(params);
            }

            reply = resp.toString();
            CharArray c = new CharArray(reply.toCharArray(),0,reply.length(),false);
            session.sendMessage(new TextMessage(c));





        }

    }


}

