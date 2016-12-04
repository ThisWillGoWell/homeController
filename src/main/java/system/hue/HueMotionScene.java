package system.hue;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHScene;
import controller.Engine;

import java.util.ArrayList;

/**
 * Created by Willi on 11/21/2016.
 */
public class HueMotionScene {
    long startTime;
    long cycleTime = 5000;
    PHBridge bridge;
    ArrayList<PHLight> lights;
    HueMotionScene(PHBridge bridge, ArrayList<PHLight> lights,  long cycleTime){
        this.cycleTime = cycleTime;
        this.lights = lights;
        this.bridge = bridge;
    }

    static HueMotionScene Rainbow(ArrayList<PHLight> lights){
    //    return new RainbowScene(lights);
        return null;
    }

    void update()
    {

    }

}

class RainbowScene extends HueMotionScene{
    private long startTime;
    RainbowScene(PHBridge bridge, ArrayList<PHLight> lights){
        super(bridge, lights, 5000);
        startTime =System.currentTimeMillis();
    }

    void update(){
        //int currentHue = (System.currentTimeMillis() - startTime )/cycleTime;
    }


}
