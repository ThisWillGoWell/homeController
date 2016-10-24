package system.hvac;

import controller.Engine;
import org.springframework.scheduling.annotation.Scheduled;
import system.SystemParent;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Will on 9/3/2016.
 */

public class HvacSystem extends SystemParent{



    private HvacSystemState state;
    private double THRESHOLD = 0.75;
    private int mode;

    public HvacSystem(Engine e)
    {
        super(e);
        state = new HvacSystemState();
        mode = 0;
    }

    @Override
    public Object get(String what, Map<String, String> requestParams) {
        switch (what)
        {
            case "state":
                return state.getStateJSON();

            case "systemTemp":
                return state.getSystemTemp();

            case "roomTemp":
                return state.getRoomTemp();

            case "mode":
                return state.getMode();
            case "acState":
                return state.getAc();

            case "heatState":
                return state.getHeat();

            case "fanState":
                return state.getFan();
            default:
                return what + " not suppored set for HVAC";

        }
    }

    @Override
    public String set(String what, String to, Map<String, String> requestParams) {
        switch (what)
        {
            case "systemTemp":
                if(isNumeric(to)) {
                    state.setSystemTemp(Double.parseDouble(to));
                    return state.getSystemTemp() + "";
                }
                else{
                    return to + " is not supported for " + what;
                }

            case "roomTemp":
                if(isNumeric(to)){
                    setRoomTemp(Double.parseDouble(to));
                    return state.getRoomTemp() + "";}
                else
                {
                    return to + " is not supported for " + what;
                }


            case "mode":
                if(Objects.equals(to, HvacSystemState.MODE_OFF_S )){
                     state.setMode(HvacSystemState.MODE_OFF);
                }
                else if(Objects.equals(to, HvacSystemState.MODE_COOL_S)) {
                    state.setMode(HvacSystemState.MODE_COOL);
                }
                else if(Objects.equals(to, HvacSystemState.MODE_HEAT_S)) {
                    state.setMode(HvacSystemState.MODE_HEAT);
                }
                else if(Objects.equals(to, HvacSystemState.MODE_FAN_S)) {
                    state.setMode(HvacSystemState.MODE_FAN);
                }
                else
                {
                    return  to + " not supported for " + what;
                }
                return to;
            default:
                return what + " not supported set for HVAC";
        }


    }

    @Override
    public String getStateJSON() {
        return null;
    }

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


    private void setRoomTemp(double d){ state.setRoomTemp(d);}
    private void setSystemTemp(double d) {
        state.setSystemTemp(d);
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }




}

