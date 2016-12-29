package system.hue;

import com.philips.lighting.model.PHLight;
import controller.Parcel;

/**
 * Created by Willi on 12/27/2016.
 */
public class HueParcel{

    /**
    *
    * Power
    *
    */

    public static Parcel SET_LIGHT_ON_PARCEL(String light){

        Parcel p = Parcel.SET_PARCEL("lights","light", "on");
        p.put("light", light);
        return p;
    }

    public static Parcel SET_LIGHT_ON_PARCEL(PHLight light){
        String what = HueSystem.ID2Name.get(light.getUniqueId());
        Parcel p = Parcel.SET_PARCEL("lights","light", "on");
        p.put("light",what);
        return p;
    }

    public static Parcel SET_ALL_LIGHTS_ON_PARCEL(){
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "on");
        return p;
    }

    public static Parcel SET_LIGHT_OFF_PARCEL(PHLight light){
        String what = HueSystem.ID2Name.get(light.getUniqueId());
        Parcel p = Parcel.SET_PARCEL("lights",what, "off");
        return p;
    }

    public static Parcel SET_ALL_LIGHTS_OFF_PARCEL(){
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "off");
        return p;
    }

    /**
     * HSV
     */

    public static Parcel SET_LIGHT_HSV_PARCEL(PHLight light, int H, int S, int V){
        Parcel p = Parcel.SET_PARCEL("lights","light", "HSV");
        p.put("light", HueSystem.ID2Name.get(light.getUniqueId()));
        p.put("H", H);
        p.put("S", S);
        p.put("V", V);
        return p;
    }

    public static Parcel SET_LIGHT_HSV_PARCEL(String light, int H, int S, int V){
        Parcel p = Parcel.SET_PARCEL("lights","light", "HSV");
        p.put("light",light);
        if(H != -1)
            p.put("H", H);
        if(S != -1)
            p.put("S", S);
        if(V != -1)
            p.put("V", V);
        return p;
    }

    public static Parcel SET_ALL_LIGHTS_HSV_PARCEL( int H, int S, int V){
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "HSV");
        p.put("H", H);
        p.put("S", S);
        p.put("V", V);
        return p;
    }

    /**
     * RGB
     */
    public static Parcel SET_LIGHT_RGB_PARCEL(PHLight light, int R, int G, int B){
        Parcel p = Parcel.SET_PARCEL("lights","light", "HSV");
        p.put("light", HueSystem.ID2Name.get(light.getUniqueId()));
        p.put("R", R);
        p.put("G", G);
        p.put("B", B);
        return p;
    }

    public static Parcel SET_LIGHT_RGB_PARCEL(String light, int R, int G, int B){
        Parcel p = Parcel.SET_PARCEL("lights","light", "HSV");
        p.put("light",light);
        p.put("R", R);
        p.put("G", G);
        p.put("B", B);
        return p;
    }

    public static Parcel SET_ALL_LIGHTS_RGB_PARCEL( int R, int G, int B){
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "HSV");
        p.put("R", R);
        p.put("G", G);
        p.put("B", B);
        return p;
    }

    /**
     *
     * Trans Time
     *
     */
    static Parcel SET_ALL_LIGHTS_LONG_TRANSTIME_PARCEL()
    {
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "longTransTime");
        return p;
    }

    static Parcel SET_LIGHT_LONG_TRANSTIME_PARCEL(PHLight light)
    {
        String l = HueSystem.ID2Name.get(light.getUniqueId());
        Parcel p = Parcel.SET_PARCEL("lights","light", "longTransTime");
        p.put("light",l);

        return p;

    }
    static Parcel SET_LIGHT_LONG_TRANSTIME_PARCEL(String light)
    {
        Parcel p = Parcel.SET_PARCEL("lights","light", "longTransTime");
        p.put("light",light);
        return p;
    }

    static Parcel SET_ALL_LIGHTS_NO_TRANSTIME_PARCEL()
    {
        Parcel p = Parcel.SET_PARCEL("lights","allLights", "noTransTime");
        return p;
    }

    static Parcel SET_LIGHT_NO_TRANSTIME_PARCEL(PHLight light)
    {
        String l = HueSystem.ID2Name.get(light.getUniqueId());
        Parcel p = Parcel.SET_PARCEL("lights","light", "noTransTime");
        p.put("light",l);

        return p;

    }
    static Parcel SET_LIGHT_NO_TRANSTIME_PARCEL(String light)
    {
        Parcel p = Parcel.SET_PARCEL("lights","light", "noTransTime");
        p.put("light",light);
        return p;
    }





}
