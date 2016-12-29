package system;


import controller.Engine;
import controller.Parcel;
import controller.ParcelException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Willi on 9/26/2016.
 */


public abstract class SystemParent implements Runnable {

    protected Engine engine;
    private long updateInterval;
    private ArrayList<WebSocketSession> webSocketSessions;

    public SystemParent(Engine e)
    {
        this(e,10000);
    }
    public SystemParent(Engine e, long updateInterval)
    {
        this.updateInterval = updateInterval;
        this.engine = e;
        webSocketSessions = new ArrayList<>();
    }

    public void registerSocket(WebSocketSession ws){
        webSocketSessions.add(ws);
    }

    public void deregisterSocket(WebSocketSession ws){
        webSocketSessions.remove(ws) ;
    }

    /*
    Command: Replacement for /set and /get
    All calls will be routed though this command interface and
    use the dict to extrace the operation: "op"
     */
    public Parcel command(Parcel p){
        /*
        take care of any high level command here,
        Only one currently in use is register for websocket listener
         */
        try {
            switch (p.getString("op")){
                default:
                    return process(p);
                case "register":
                    registerSocket(p.getWebsocketSession("ws"));
                    return Parcel.RESPONSE_PARCEL("register success");
                case "deregister":
                    deregisterSocket(p.getWebsocketSession("ws"));
                    return Parcel.RESPONSE_PARCEL("deregister success");
            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }

    }

    public abstract Parcel process(Parcel p);

    /*
    Send Meg:
    Send a message to all websocket sessions
     */
    public void sendMsg(Parcel p){
        sendMsg(p.toString());
    }
    public void sendMsg(String s){
        for(WebSocketSession ws : webSocketSessions){
            try {
                ws.sendMessage(new TextMessage(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public long getUpdateInterval()
    {
        return updateInterval;
    }

    public void run(){
        try {
            while(true) {
                update();
                Thread.sleep(updateInterval);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    @Todo
    remove: replace with /command
    replace missingRequired with an int error
     */



    public void update(){

    }
}
