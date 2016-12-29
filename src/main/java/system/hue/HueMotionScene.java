package system.hue;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.model.PHScene;
import controller.Engine;
import controller.Parcel;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Willi on 11/21/2016.
 * Class to manage Hue motion Scenes
 *
 */


class HueMotionScene {
    long startTime;
    long updateInterval ;
    protected long lastUpdateTime;
    HueSystem system;
    ArrayList<PHLight> lights;

    /*
    Keep track of all the lights involved with the pattern
     */
    HueMotionScene(HueSystem system, long updateInterval){
        lights = new ArrayList<>();
        this.updateInterval = updateInterval;
        this.system = system;
        this.lastUpdateTime = System.currentTimeMillis();
        this.startTime = lastUpdateTime;
    }

    long getUpdateTime(){
        return updateInterval;
    }

    /*
    on update, check if they need to be ran again
    if yes, do one "step" of the program
    @NOTE: if the system laggs at all, then steps will be missed ie missed steps are missed
     */
    void update() {
        if (lastUpdateTime + updateInterval <= System.currentTimeMillis()) {
            lastUpdateTime = System.currentTimeMillis();
            step();
        }
        else
        {
            int k = 0;
        }
    }

    void step(){

    }


    static HueMotionScene Rainbow(ArrayList<PHLight> lights){
    //    return new RainbowScene(lights);
        return null;
    }




}

class RainbowScene extends HueMotionScene{
    private long cycleTime;

    RainbowScene(HueSystem system){
        super(system, 500);
        startTime =System.currentTimeMillis();
        cycleTime = 10000;
        //Determine order;
        lights.add(system.name2Light.get("tv"));
        lights.add(system.name2Light.get("strip"));
        lights.add(system.name2Light.get("lamp"));
        lights.add(system.name2Light.get("door"));
        lights.add(system.name2Light.get("bathroom"));
        system.process(Parcel.SET_PARCEL("","allLights","off"));

        for(PHLight light:lights) {
            system.process(HueParcel.SET_LIGHT_HSV_PARCEL(light, 65535,65535,65535));
            system.process(HueParcel.SET_LIGHT_ON_PARCEL(light));
        }
        system.process(HueParcel.SET_ALL_LIGHTS_LONG_TRANSTIME_PARCEL());

        step();
    }

    void step(){
        //Determine
        int count = 0;
        long startHue = ((System.currentTimeMillis() - startTime) % cycleTime) *65535/cycleTime ;
        long k = (System.currentTimeMillis() - startTime);
        for(PHLight light : lights){
            system.process(HueParcel.SET_LIGHT_HSV_PARCEL(light, (int) (startHue +  (65535/lights.size() * count) % 65535), -1,-1));
            count++;
        }

    }
}

class HueShiftScene extends HueMotionScene{
    private long cycleTime;

    HueShiftScene(HueSystem system){
        super(system, 500);
        startTime =System.currentTimeMillis();
        cycleTime = 10000;
        //Determine order;
        lights.add(system.name2Light.get("tv"));
        lights.add(system.name2Light.get("strip"));
        lights.add(system.name2Light.get("lamp"));
        lights.add(system.name2Light.get("door"));
        lights.add(system.name2Light.get("bathroom"));

        for(PHLight light:lights) {
            system.process(HueParcel.SET_LIGHT_HSV_PARCEL(light, 65535,65535,65535));
            system.process(HueParcel.SET_LIGHT_ON_PARCEL(light));
        }
        system.process(HueParcel.SET_ALL_LIGHTS_LONG_TRANSTIME_PARCEL());
        step();
    }

    void step(){
        //Determine
        int count = 0;
        long startHue = ((System.currentTimeMillis() - startTime) % cycleTime) *65535/cycleTime ;
        System.out.println(startHue + "");
        long k = (System.currentTimeMillis() - startTime);
        for(PHLight light : lights) {
            system.process(HueParcel.SET_LIGHT_HSV_PARCEL(light, (int) startHue, -1, -1));
        }
    }
}



