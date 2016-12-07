package controller;

import org.json.hue.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Willi on 10/25/2016.
 * Class to handle the websocket
 * communication to /ws
 *
 */
@Configuration
@EnableWebSocket
public class WebsocketHandler extends TextWebSocketHandler {

    static ArrayList<WebSocketSession> sessions = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("joined");
        session.sendMessage(new TextMessage("Hello! welcome to the party"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JSONObject m = new JSONObject(message.getPayload());
        Map<String,String> params = new HashMap<>();
        Object resp = null;
        String reply;
        for(Object key : m.keySet()){
            String k = (String) key;
            params.put(k,m.getString(k));
        }
        if(Objects.equals(params.get("op"), "get")){
            resp =  Application.getEngine().get(params);
        }
        else if(Objects.equals(params.get("op"), "set")){
            resp =  Application.getEngine().set(params);
        }
        session.sendMessage(new TextMessage( resp.toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
        System.out.println("Lost a session " + status.getReason());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.out.println("Error");
    }
}
