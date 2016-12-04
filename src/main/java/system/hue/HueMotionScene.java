package system.hue;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.model.PHScene;
import controller.Engine;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Willi on 11/21/2016.
 */


class HueMotionScene {
    long startTime;
    long updateInterval ;
    protected long lastUpdateTime;
    HueSystem system;
    ArrayList<PHLight> lights;
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

        Map<String, String> state = new HashMap<>();
        for(PHLight light:lights) {
            state.put("S", (65535) + "");
            state.put("V", (65535) + "");
            system.setLight(light, "on", null);
            system.setLight(light,"HSV",state);
        }
        system.setAllLights("longTransTime",null);
        step();
    }

    void step(){
        //Determine
        int count = 0;
        long startHue = ((System.currentTimeMillis() - startTime) % cycleTime) *65535/cycleTime ;
        long k = (System.currentTimeMillis() - startTime);
        for(PHLight light : lights){
            Map<String,String> state = new HashMap<>();
            state.put("H", (startHue +  (65535/lights.size() * count) % 65535) + "");
            system.setLight(light,"HSV",state);
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

        Map<String, String> state = new HashMap<>();
        for(PHLight light:lights) {
            state.put("S", (65535) + "");
            state.put("V", (65535) + "");
            system.setLight(light, "on", null);
            system.setLight(light,"HSV",state);
        }
        system.setAllLights("longTransTime",null);
        step();
    }

    void step(){
        //Determine
        int count = 0;
        long startHue = ((System.currentTimeMillis() - startTime) % cycleTime) *65535/cycleTime ;
        System.out.println(startHue + "");
        long k = (System.currentTimeMillis() - startTime);
        for(PHLight light : lights){
            Map<String,String> state = new HashMap<>();
            state.put("H", startHue + "");
            system.setLight(light,"HSV",state);
        }

    }
}



