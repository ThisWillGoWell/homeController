package controller;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import parcel.Parcel;
import system.SystemParent;

import java.io.IOException;

/**
 * Created by Willi on 12/30/2016.
 */
public class SocketSession implements Subscriber{
    WebSocketSession session;
    public SocketSession(WebSocketSession session){
        this.session = session;
    }

    @Override
    public void subscriptionAlert(Parcel p) {
        try {
            session.sendMessage(new TextMessage(p.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
