package system.chromecast;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;

import java.io.IOException;

/**
 * Created by Willi on 12/4/2016.
 */
public class Chromecast {
    public static void main(String args[]){
        try {
            System.out.println("yoo");
            ChromeCasts.startDiscovery();
            Thread.sleep(5000);
            for(ChromeCast cast: ChromeCasts.get()){
                System.out.println(cast.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
