package system.hue;


import com.philips.lighting.hue.listener.PHSceneListener;
import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.heartbeat.PHHeartbeatManager;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;
import controller.Engine;
import controller.Parcel;
import controller.ParcelException;
import system.SystemParent;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Willi on 9/26/2016.
 * USes the HUE SDK to talk to the hue system
 * @Todo alot, mostly check, handle not found stuffs
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

    static Map<String, String> ID2Name =  populateID2Name();
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

    /*
    Hue System must update once every 10 ms for motion animations
     */
    public HueSystem(Engine e)
    {
        super(e, 10);
        eventListener = new HueEventListener(this);
        phHueSDK = PHHueSDK.getInstance();

        //Hash to manage Light Name to its last state;
        lastState = new HashMap<String, PHLightState>();
        //hash to manage Unique ID 2 name, I populate this



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
                 //   System.out.println("Lights Cache Updated ");
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



    /*
    The current list of Light IDs and what I call them.
    Used when only thing have access to is the ID
     */
    private static Map populateID2Name()
    {
        HashMap<String,String> m = new HashMap<>();
        m.put("00:17:88:01:10:56:a4:5a-0b", "bathroom");
        m.put("00:17:88:01:10:56:a4:0d-0b", "lamp");
        m.put("00:17:88:01:10:56:a4:2c-0b", "tv");
        m.put("00:17:88:01:01:1a:aa:5b-0b", "strip");
        m.put("00:17:88:01:00:f7:1a:02-0b", "door");
        m.put("00:17:88:01:10:2d:97:e3-0b", "fanWhite1");
        m.put("00:17:88:01:10:2f:88:27-0b", "fanWhite2");
        return m;
    }



    /*
    Loop thogh all lights in the ID2Name list and match them to a PLight object
     */
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


    /*
    Makes dealing with hue scens alot easier because I can just call them by their
    name and get their sceneID
     */
    private void populateName2Scene(){
        for(PHScene scene : bridge.getResourceCache().getAllScenes()){
            name2SceneID.put(scene.getName(),scene.getSceneIdentifier());
        }
    }

    /*
    @TODO return error code when not found
     */

    public Parcel process(Parcel p){
        try {
            switch (p.getString("op")){
                case "get":
                    return  get(p);
                case "set":
                    return set(p);
                default:
                    throw ParcelException.OP_NOT_SUPPORTED(p);
            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }
    }

    private Parcel get(Parcel p) {
        try {
            switch (p.getString("what"))
            {
                case "mode":
                    return Parcel.RESPONSE_PARCEL(currentMode);
                default:
                    throw ParcelException.WHAT_NOT_SUPPORTED(p);

            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }
    }

    private Parcel set(Parcel p) {
        try {
            switch (p.getString("what"))
            {
                case "mode":
                    setMode(p);
                    break;
                case "allLights":
                    setAllLights(p);
                    break;
                case "colorLights":
                    setAllColorLights(p);
                    break;
                case "light":
                    setLight(p);
                    break;
                default:
                    throw ParcelException.WHAT_NOT_SUPPORTED(p);
            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }
        return Parcel.RESPONSE_PARCEL(null);
    }

    /*
    Useing the String "to" do the required actions to set the program in that mode
     */
    private void setMode(Parcel p) throws ParcelException{
        switch (p.getString("to")){
            case "off":
                allOff();
                currentMotionScene = null;
                liveMode = false;
                break;
            case "bright":
                liveMode = false;
                currentMotionScene = null;
                bridge.activateScene(name2SceneID.get("Concentrate"), "0", sceneListener);
                allOn();
                break;
            case "dim":
                liveMode =false;
                currentMotionScene = null;
                bridge.activateScene(name2SceneID.get("Nightlight"), "0", sceneListener);
                break;

            case "standard":
                currentMotionScene = null;
                liveMode = false;
                bridge.activateScene(name2SceneID.get("standard"),"0",sceneListener);
                allOn();
                break;

            case "rainbow":
                liveMode = true;
                currentMode = "rainbow";
                currentMotionScene = new RainbowScene(this);
                break;

            default:
                throw ParcelException.TO_NOT_SUPPORTED(p);
        }
    }


    /*
    Set a PHlight using strings
    @TODO dont crash on invalid input
     */
    private void setLight(PHLight light, Parcel p) throws ParcelException{
        PHLightState newState = new PHLightState();
        switch (p.getString("to")) {
            case "HSV":
                if(p.containsKey("H"))
                    newState.setHue(p.getInteger("H"));
                if(p.containsKey("S"))
                    newState.setHue(p.getInteger("S"));
                if(p.containsKey("V"))
                    newState.setHue(p.getInteger("V"));

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
                float xy[] = PHUtilities.calculateXYFromRGB(p.getInteger("R"),p.getInteger("G"),p.getInteger("B"), light.getModelNumber());
                newState.setX(xy[0]);
                newState.setY(xy[1]);
                bridge.updateLightState(light, newState);
                break;
            default:
                throw ParcelException.TO_NOT_SUPPORTED(p);
        }
    }

    private void setLight(Parcel p) throws ParcelException {
        setLight(name2Light.get(p.getString("light")), p);
    }

    /*
    Sets all the colored light to something
     */
    private void setAllColorLights(Parcel p) throws ParcelException{
        for(PHLight light : allLights){
            if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT))
            {
                setLight(light, p);
            }
        }
    }

    /*
    Set all the lights to something
    @Todo use groups to have it all change at once
    @todo dont break on improper input
    @todo return int error
     */
    private void setAllLights(Parcel p) throws ParcelException{
        PHLightState state = new PHLightState();
        switch (p.getString("to")){
            case "on":
                allOn();
                break;
            case "off":
                allOff();
                break;
            case "HSV":
                for(PHLight light : allLights){
                    if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT)){
                        setLight(light,p);
                    }
                }
                break;
            case "RGB":
                for(PHLight light: allLights){
                    if(light.getLightType().equals(PHLight.PHLightType.COLOR_LIGHT)){
                        setLight(light, p);
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

    /*
    Turn allvthe lights off
     */
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

    /*
    On update, check if there needs to be anything changed for the current mode
    let the mode handel the acutal change
     */
    @Override
    public void update()
    {
       // System.out.println("Lights:");
        if(bridge != null) {
            for (PHLight light : bridge.getResourceCache().getAllLights()) {
             //   System.out.println(ID2Name.get(light.getUniqueId()) + ":\t" + light.getLastKnownLightState().isReachable());
            }
            if (currentMotionScene != null) {
                currentMotionScene.update();
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
          //  System.out.println(s.getName());
        }


        while(true) {
            try {
                system.update();
                Thread.sleep(100);
                for(PHLight light:system.allLights){
                   // System.out.print(light.getLastKnownLightState());
                }
              // System.out.println();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //system.setAllLightsRGB(69,47,42);
        //system.allOn();

    }




}

