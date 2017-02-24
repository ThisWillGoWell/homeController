package system;


import controller.Engine;
import controller.SocketSession;
import controller.Subscriber;
import controller.SubscriberManager;
import parcel.Parcel;
import parcel.SystemException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Willi on 9/26/2016.
 */


public abstract class SystemParent implements Runnable, Subscriber {

    protected Engine engine;
    private long updateInterval;

    public SystemParent(Engine e)
    {
        this(e,10000);
    }
    public SystemParent(Engine e, long updateInterval)
    {
        this.updateInterval = updateInterval;
        this.engine = e;
        SubscriberManager.register(this);
    }
    private void registerSubscriber(Parcel p) throws SystemException {
        SubscriberManager.subscribe(p.getSubscriber("subscriber"), this, Parcel.GET_PARCEL(p.getString("system"), p.getString("what")));
    }

    private void deregisterSubscriber(Subscriber s){
        SubscriberManager.unsubscribe(s, this);
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
        Parcel response = null;
        try {
            switch (p.getString("op")){
                default:
                    return process(p);
                case "subscribe":
                    registerSubscriber(p);
                    response=  Parcel.RESPONSE_PARCEL("register success");
                    break;
                case "deregister":
                    deregisterSubscriber(p.getSubscriber("subscriber"));
                    response = Parcel.RESPONSE_PARCEL("deregister success");
                    break;
                case "update":
                    update();
                    response = Parcel.RESPONSE_PARCEL("updated");
                    break;
            }
        } catch (SystemException e) {
                return Parcel.RESPONSE_PARCEL_ERROR(e);
        }
        return response;
    }

    public abstract Parcel process(Parcel p);


    public Engine getEngine() {
        return engine;
    }

    public void run(){
        try {
            while(true) {
                update();
                SubscriberManager.checkUpdate(this);
                Thread.sleep(updateInterval);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void subscriptionAlert(Parcel p){

    }

    public void update(){

    }
}
