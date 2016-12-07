package system.hue;


import com.philips.lighting.hue.listener.PHSceneListener;
import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;
import controller.Engine;
import system.SystemParent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Willi on 9/26/2016.
 *
 */

public class HueSystem extends SystemParent{
    private PHHueSDK phHueSDK;
    private final String HUE_IP = "192.168.1.140";
    List<PHLight> allLights;
    ArrayList<String> lightIdentifiers;
    private PHBridge bridge;
    private PHSDKListener listener;
    private PHBridgeResourcesCache cache;
    private String HUE_USERNAME = "iixA66asLRYI-jOBsmrwjIhpu7VYkTl1R1CitgZa";
    private boolean connected;
    private HueEventListener eventListener;
    private Map<String, PHLightState> lastState;

    Map<String, String> ID2Name;
    Map<String, PHLight> name2Light;
    private boolean liveMode;
    private String currentMode = "";
    private HueMotionScene currentMotionScene;
    private Map<String, String> name2SceneID;

    void print(String s)
    {
        System.out.println(s);
    }



    private PHSceneListener sceneListener = new PHSceneListener() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(int i, String s) {
            System.out.println(s);
        }

        @Override
        public void onStateUpdate(Map<String, String> map, List<PHHueError> list) {

        }

        @Override
        public void onScenesReceived(List<PHScene> list) {

        }

        @Override
        public void onSceneReceived(PHScene phScene) {

        }
    };
    public HueSystem(Engine e)
    {
        super(e, 100);
        eventListener = new HueEventListener(this);
        phHueSDK = PHHueSDK.getInstance();

        //Hash to manage Light Name to its last state;
        lastState = new HashMap<String, PHLightState>();
        //hash to manage Unique ID 2 name, I populate this
        ID2Name = new HashMap<String, String>();
        populateID2Name();

        //manages Name to it light
        name2Light = new HashMap<String, PHLight>();
         liveMode = false;
        name2SceneID = new HashMap<>();


        listener = new PHSDKListener() {
            @Override
            public void onAccessPointsFound(List accessPoint) {
                // Handle your bridge search results here.  Typically if multiple results are returned you will want to display them in a list
                // and let the user select their bridge.   If one is found you may opt to connect automatically to that bridge.

            }

            @Override
            public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {
                // Here you receive notifications that the BridgeResource Cache was updated. Use the PHMessageType to
                // check which cache was updated, e.g.
                if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
                    //System.out.println("Lights Cache Updated ");
                    //processLightChange();

                }
            }

            @Override
            public void onBridgeConnected(PHBridge b, String username) {
                System.out.println("Bridge Connected: " + username);
                phHueSDK.setSelectedBridge(b);
                PHHeartbeatManager heartbeatManager = PHHeartbeatManager.getInstance();

                heartbeatManager.enableLightsHeartbeat(b, 500);
                bridge = b;
                cache = b.getResourceCache();
                allLights = cache.getAllLights();
                connected = true;
                //Populate
                populateName2Light();
                populateName2Scene();
                // Here it is recommended to set your connected bridge in your sdk object (as above) and start the heartbeat.
                // At this point you are connected to a bridge so you should pass control to your main program/activity.
                // The username is generated randomly by the bridge.
                // Also it is recommended you store the connected IP Address/ Username in your app here.  This will allow easy automatic connection on subsequent use.
            }

            @Override
            public void onAuthenticationRequired(PHAccessPoint accessPoint) {
                System.out.println("Auth Required");
                phHueSDK.startPushlinkAuthentication(accessPoint);
                // Arriving here indicates that Pushlinking is required (to prove the User has physical access to the bridge).  Typically here
                // you will display a pushlink image (with a timer) indicating to to the user they need to push the button on their bridge within 30 seconds.
            }

            @Override
            public void onConnectionResumed(PHBridge bridge) {

            }

            @Override
            public void onConnectionLost(PHAccessPoint accessPoint) {
                // Here you would handle the loss of connection to your bridge.
            }

            @Override
            public void onError(int code, final String message) {
                // Here you can handle events such as Bridge Not Responding, Authentication Failed and Bridge Not Found
                System.out.println(message);
            }

            @Override
            public void onParsingErrors(List parsingErrorsList) {
                // Any JSON parsing errors are returned here.  Typically your program should never return these.
            }


        };
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(HUE_IP);
        accessPoint.setUsername(HUE_USERNAME);
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        phHueSDK.connect(accessPoint);
        phHueSDK.setAppName("Home Control");
        phHueSDK.setDeviceName("server");



    }

    private void populateID2Name()
    {
        ID2Name.put("00:17:88:01:10:56:a4:5a-0b", "bathroom");
        ID2Name.put("00:17:88:01:10:56:a4:0d-0b", "lamp");
        ID2Name.put("00:17:88:01:10:56:a4:2c-0b", "tv");
        ID2Name.put("00:17:88:01:01:1a:aa:5b-0b", "strip");
        ID2Name.put("00:17:88:01:00:f7:1a:02-0b", "door");
        ID2Name.put("00:17:88:01:10:2d:97:e3-0b", "fanWhite1");
        ID2Name.put("00:17:88:01:10:2f:88:27-0b", "fanWhite2");


    }

    private void populateName2Light(){
        for(String id : ID2Name.keySet()){
            for(PHLight light : allLights){
                try {
                    name2Light.put(ID2Name.get(light.getUniqueId()), light);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }


    private void populateName2Scene(){
        for(PHScene scene : bridge.getResourceCache().getAllScenes()){
            name2SceneID.put(scene.getName(),scene.getSceneIdentifier());
        }
    }
    @Override
    public Object get(String what, Map<String, String> requestParams) {
        switch (what)
        {
            case "mode":
                return currentMode;

        }
        return "";
    }

    @Override
    public String set(String what, String to, Map<String, String> requestParams) {
        switch (what)
        {
            case "mode":
                setMode(to, requestParams);
            case "allLights":
                setAllLights(to, requestParams);
                break;
            case "colorLights":
                setAllColorLights(to, requestParams);
                break;
            case "bathroom":
                setLight("bathroom", to, requestParams);
                break;

        }
        return null;
    }

    private void setMode(String to, Map<String, String> requestParams) {
        switch (to){
            case "off":
                allOff();
                liveMode = false;
                break;
            case "bright":
                liveMode = false;
                bridge.activateScene(name2SceneID.get("Concentrate"), "0", sceneListener);
                allOn();
                break;
            case "dim":
                liveMode =false;
                bridge.activateScene(name2SceneID.get("Nightlight"), "0", sceneListener);
                break;

            case "standard":

                liveMode = false;
                bridge.activateScene(name2SceneID.get("standard"),"0",sceneListener);
                allOn();
                break;

            case "rainbow":
                liveMode = true;
                currentMode = "rainbow";
                currentMotionScene = new RainbowScene(this);

        }
    }


    void setLight(PHLight light, String to, Map<String, String> requestParams){
        PHLightState newState = new PHLightState();
        switch (to) {
            case "HSV":
                if (requestParams.containsKey("H")) {
                    newState.setHue(Integer.valueOf(requestParams.get("H")), true);
                }
                if (requestParams.containsKey("S")) {
                    newState.setSaturation(Integer.valueOf(requestParams.get("S")), true);
                }
                if (requestParams.containsKey("V")) {
                    newState.setBrightness(Integer.valueOf(requestParams.get("V")), true);
                }
                bridge.updateLightState(light, newState);
                break;
            case "off":
                newState.setOn(false);
                bridge.updateLightState(light,newState);
                break;

            case "on":
                newState.setOn(true);
                bridge.updateLightState(light,newState);
                break;

            case "RGB":
                if(requestParams.containsKey("R") && requestParams.containsKey("G") && requestParams.containsKey("B")) {
                    int R = Integer.parseInt(requestParams.get("R"));
                    int G = Integer.parseInt(requestParams.get("G"));
                    int B = Integer.parseInt(requestParams.get("B"));

                    float xy[] = PHUtilities.calculateXYFromRGB(R, G, B, light.getModelNumber());
                    PHLightState lightState = new PHLightState();
                    lightState.setX(xy[0]);
                    lightState.setY(xy[1]);
                    bridge.updateLightState(light, lightState);
                }
        }
    }

    void setLight(String lightName, String to, Map<String, String> requestParams)
    {
        setLight(name2Light.get(lightName), to, requestParams);
    }

    private void setAllColorLights(String to, Map<String, String> requsetParams){
        for(PHLight light : allLights){
            if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT))
            {
                setLight(light, to, requsetParams);
            }
        }
    }

    void setAllLights(String to, Map<String, String> requestParams){
        PHLightState state = new PHLightState();
        switch (to){
            case "on":
                allOn();
                break;
            case "off":
                allOff();
                break;
            case "HSV":
                for(PHLight light : allLights){
                    if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT)){
                        setLight(light, to, requestParams);
                    }
                }
                break;
            case "RGB":
                for(PHLight light: allLights){
                    if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT)){
                        setLight(light, to, requestParams);
                    }
                }
                break;
            case "longTransTime":
                state = new PHLightState();
                state.setTransitionTime(7);
                for(PHLight light: allLights){
                    bridge.updateLightState(light,state);
                }
                break;

            case "noTransTime":
                state.setTransitionTime(0);
                for(PHLight light: allLights){
                    bridge.updateLightState(light,state);
                }
                break;




        }
    }


    private void allOff()
    {
        PHLightState lightState = new PHLightState();
        lightState.setOn(false);
        bridge.setLightStateForDefaultGroup(lightState);
    }

    private void allOn()
    {
        PHLightState lightState = new PHLightState();
        lightState.setOn(true);
        bridge.setLightStateForDefaultGroup(lightState);
    }

    private void setAllLightsRGB(int R, int G, int B){
        for(PHLight light : allLights){
            float xy[] = PHUtilities.calculateXYFromRGB(R, G, B, light.getModelNumber());
            PHLightState lightState = new PHLightState();
            lightState.setX(xy[0]);
            lightState.setY(xy[1]);

            bridge.updateLightState(light, lightState);
        }
    }


    public void update()
    {
        if(liveMode){
            switch (currentMode){
                case "rainbow":
                    //currentMode = HueMotionScene.Rainbow(RainbowLights);
                    currentMotionScene.update();
                    break;
            }
        }

    }

    private void processLightChange()
    {

        //Process light change

        //UpdateCache
        updateCache();

    }

    private void updateCache()
    {
        for(PHLight light : allLights)
        {
            lastState.put(light.getUniqueId(), light.getLastKnownLightState());
        }
    }





    public static void main(String[] args)
    {
        HueSystem system = new HueSystem(null);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //system.setAllLightsRGB(255,0,255);

        for(PHScene s: system.cache.getAllScenes()){
            System.out.println(s.getName());
        }
        system.set("allLights", "off", null);

        system.setMode("standard", null);

        while(true) {
            try {
                system.update();
                Thread.sleep(100);
                for(PHLight light:system.allLights){
                    System.out.print(light.getLastKnownLightState());
                }
                System.out.println();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //system.setAllLightsRGB(69,47,42);
        //system.allOn();

    }




}

