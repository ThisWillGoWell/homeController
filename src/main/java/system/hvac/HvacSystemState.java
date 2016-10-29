package system.hvac;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Created by Will on 9/3/2016.
 */
public class HvacSystemState {


    HashMap<Integer, String> modeMap;

    final static String MODE_OFF_S = "off";
    final static int MODE_OFF = 0;
    final static String MODE_COOL_S = "cool";
    final static int MODE_COOL = 1;
    final static String MODE_HEAT_S = "heat";
    final static int MODE_HEAT = 2;
    final static String MODE_FAN_S = "fan";
    final static int MODE_FAN = 3;


    private int mode;


    private boolean heat;
    private boolean ac;
    private boolean fan;

    private double roomTemp;
    private double systemTemp;


    HvacSystemState()
    {
        mode = MODE_OFF;
        modeMap = new HashMap<>(4);
        modeMap.put(MODE_COOL, MODE_COOL_S);
        modeMap.put(MODE_FAN, MODE_FAN_S);
        modeMap.put(MODE_OFF, MODE_OFF_S);
        modeMap.put(MODE_HEAT, MODE_HEAT_S);

        heat = false;
        ac = false;
        fan = false;

        roomTemp = 27;
        systemTemp = 27;


    }

    boolean getHeat()
    {
        return heat;
    }

    void setHeat(boolean b)
    {
        heat = b;
    }

    boolean getAc()
    {
        return ac;
    }

    void setAc(boolean b)
    {
        ac = b;
    }

    boolean getFan()
    {
        return fan ;
    }

    void setFan(boolean b)
    {
        fan = b;
    }

    void setRoomTemp(double temp)
    {
        roomTemp = temp;
    }

    double getRoomTemp()
    {
        return roomTemp;
    }

    void setSystemTemp(double d)
    {
        systemTemp = d;
    }

    double getSystemTemp()
    {
        return systemTemp;
    }

    void setMode(int m)   {mode = m;}
    int getMode()    {return mode;}

    public JsonObject getStateJSON()
    {
        JsonObject json = new JsonObject();
        JsonObject state = new JsonObject();
        state.addProperty("mode",modeMap.get(mode));
        state.addProperty("heatState",heat);
        state.addProperty("acState", ac);
        state.addProperty("fanState",fan);
        state.addProperty("roomTemp", roomTemp);
        state.addProperty("systemTemp",systemTemp);
        return state;

    }


}
