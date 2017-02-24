package controller;

import parcel.Parcel;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.xml.ws.handler.HandlerResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Willi on 10/25/2016.
 * Class to handle the websocket
 * communication to /ws
 *
 */
@Configuration
@EnableWebSocket
public class WebsocketHandler extends TextWebSocketHandler implements Subscriber{

    static ArrayList<WebSocketSession> sessions = new ArrayList<>();
    static ConcurrentHashMap<WebSocketSession, SocketSession> socketSessions= new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        socketSessions.put(session, new SocketSession(session));
        new Thread(socketSessions.get(session)).start();
    }

    @Override

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        Parcel p = Parcel.PROCESS_JSONSTR(message.getPayload());
        p.put("subscriber", socketSessions.get(session));
        Parcel response = Application.getEngine().command(p);
        response.put("request", p);
        System.out.println(response.toString());

        socketSessions.get(session).queueMsg(response);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        sessions.remove(session);
        SubscriberManager.unsubscribe(socketSessions.get(session));
        socketSessions.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void subscriptionAlert(Parcel p) {

    }
}
