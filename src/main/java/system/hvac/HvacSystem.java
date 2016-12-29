package system.hvac;

import com.google.gson.JsonObject;
import controller.Engine;
import controller.Parcel;
import controller.ParcelException;
import system.SystemParent;

import java.util.HashMap;

/**
 * Created by Will on 9/3/2016.
 * Class to manage the HVAC system
 * Controls Heater, AC and Fan
 *
 */

public class HvacSystem extends SystemParent{
    private HvacSystemState state;
    private double THRESHOLD = 0.75;
    private int mode;

    /*
    Class to represent the HVAC system
    Controls the heater, fan, ac
    update every 3.5 seconds
     */
    public HvacSystem(Engine e)
    {
        super(e, 3500);
        state = new HvacSystemState();
        mode = 0;
        update();
    }


    public Parcel process(Parcel p) {
        try {
            switch (p.getString("op")) {
                case "get":
                    switch (p.getString("what")) {
                        case "state":
                            return Parcel.RESPONSE_PARCEL(state.stateParcel());

                        case "systemTemp":
                            return Parcel.RESPONSE_PARCEL(state.getSystemTemp());

                        case "roomTemp":
                            return Parcel.RESPONSE_PARCEL(state.getRoomTemp());

                        case "mode":
                            return Parcel.RESPONSE_PARCEL(state.getMode());
                        case "acState":
                            return Parcel.RESPONSE_PARCEL(state.getAc());

                        case "heatState":
                            return Parcel.RESPONSE_PARCEL(state.getHeat());

                        case "fanState":
                            return Parcel.RESPONSE_PARCEL(state.getFan());
                        default:
                            throw ParcelException.WHAT_NOT_SUPPORTED(p);
                    }



                case "set":
                    switch (p.getString("what")) {
                        case "systemTemp":
                            state.setSystemTemp(p.getDouble("to"));
                            break;

                        case "roomTemp":
                           state.setRoomTemp(p.getDouble("to"));
                            break;

                        case "mode":
                            switch (p.getString("to")) {
                                case HvacSystemState.MODE_OFF_S:
                                    state.setMode(HvacSystemState.MODE_OFF);
                                    break;
                                case HvacSystemState.MODE_HEAT_S:
                                    state.setMode(HvacSystemState.MODE_HEAT);
                                    break;
                                case HvacSystemState.MODE_COOL_S:
                                    state.setMode(HvacSystemState.MODE_COOL);
                                    break;
                                case HvacSystemState.MODE_FAN_S:
                                    state.setMode(HvacSystemState.MODE_FAN);
                                    break;

                                default:
                                    throw ParcelException.TO_NOT_SUPPORTED(p);
                            }
                            break;
                        default:
                            throw ParcelException.WHAT_NOT_SUPPORTED(p);
                    }
                    return Parcel.RESPONSE_PARCEL(state.stateParcel());
                default:
                    throw ParcelException.OP_NOT_SUPPORTED(p);
            }
        } catch (ParcelException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }

    }


    /*
    Use a smhit trigger style to control the modes
    not sure why I used a state here, but it does make it easier to read?

     */
   public void update() {
        if(state.getMode() == HvacSystemState.MODE_COOL)
        {
            state.setHeat(false);

            if (state.getRoomTemp() > state.getSystemTemp() + THRESHOLD) {
                state.setAc(true);
                state.setFan(true);
            }
                /*
                 * Cool down till it gets below the threshold
                 */
            else if (state.getRoomTemp() < state.getSystemTemp() - THRESHOLD) {
                state.setAc(false);
                state.setFan(false);
            }

        }
        else if (state.getMode() == HvacSystemState.MODE_HEAT)
        {
            state.setAc(false);
            //Heat Mode
            if(state.getRoomTemp() < (state.getSystemTemp() - THRESHOLD)) {
                state.setHeat(true);
                state.setFan(true);
            }
            else if(state.getRoomTemp() > (state.getSystemTemp()+THRESHOLD)) {
                state.setHeat(false);
                state.setFan(false);
            }
        }
        else if (state.getMode() == HvacSystemState.MODE_FAN)
        {
            state.setAc(false);
            state.setFan(true );
            state.setHeat(false);
        }
        else
        {
            state.setAc(false);
            state.setHeat(false);
            state.setFan(false);
        }
    }





    class HvacSystemState {

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

        Parcel stateParcel(){
            Parcel p = new Parcel();
            p.put("mode",modeMap.get(mode));
            p.put("heatState",heat);
            p.put("acState", ac);
            p.put("fanState",fan);
            p.put("roomTemp", roomTemp);
            p.put("systemTemp",systemTemp);
            return p;
        }
    }
}

