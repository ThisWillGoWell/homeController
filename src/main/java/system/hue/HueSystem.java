package system.hue;


import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willi on 9/26/2016.
 *
 */

public class HueSystem {
    private PHHueSDK phHueSDK;
    private final String HUE_IP = "192.168.1.140";
    List<PHLight> allLights;
    ArrayList<String> lightIdentifiers;

    private PHSDKListener listener;


    HueSystem()
    {


        phHueSDK = PHHueSDK.getInstance();
        phHueSDK.setAppName("Home Control");
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
               // if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
               //     System.out.println("Lights Cache Updated ");
                //}
            }

            @Override
            public void onBridgeConnected(PHBridge b, String username) {
                phHueSDK.setSelectedBridge(b);
                phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
                // Here it is recommended to set your connected bridge in your sdk object (as above) and start the heartbeat.
                // At this point you are connected to a bridge so you should pass control to your main program/activity.
                // The username is generated randomly by the bridge.
                // Also it is recommended you store the connected IP Address/ Username in your app here.  This will allow easy automatic connection on subsequent use.
            }

            @Override
            public void onAuthenticationRequired(PHAccessPoint accessPoint) {
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
            }

            @Override
            public void onParsingErrors(List parsingErrorsList) {
                // Any JSON parsing errors are returned here.  Typically your program should never return these.
            }
        };
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);




    }

    void update()
    {

    }

    void findBridge(){
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);

    }


    /**
     * Connect to the last known access point.
     * This method is triggered by the Connect to Bridge button but it can equally be used to automatically connect to a bridge.
     *
     */
    public boolean connectToLastKnownAccessPoint() {
        String username = HueProperties.getUsername();
        String lastIpAddress =  HueProperties.getLastConnectedIP();

        if (username==null || lastIpAddress == null) {
            System.out.println("Missing Last Username or Last IP.  Last known connection not found.");
            return false;
        }
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        phHueSDK.connect(accessPoint);
        return true;
    }
}

