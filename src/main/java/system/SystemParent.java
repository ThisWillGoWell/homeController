package system;

import com.google.gson.JsonObject;
import controller.Engine;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Willi on 9/26/2016.
 */


public abstract class SystemParent implements Runnable {

    private Engine engine;
    private long updateInterval;

    public SystemParent(Engine e)
    {
        this(e,0);
    }
    public SystemParent(Engine e, long updateInterval)
    {
        this.updateInterval = updateInterval;
        this.engine = e;
    }

    public  abstract Object get(String what, Map<String, String> requestParams);
    public abstract String set(String what, String to, Map<String, String> requestParams);

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
    public void update(){

    }
}
