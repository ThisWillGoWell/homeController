package system.hvac;

import controller.Engine;
import system.SystemParent;

import java.util.Objects;

/**
 * Created by Will on 9/3/2016.
 */

public class HvacSystem extends SystemParent{


    private HvacSystemState state;
    private double THRESHOLD = 0.75;

    public HvacSystem(Engine e)
    {
        super(e);
        state = new HvacSystemState();
    }

    @Override
    public Object get(String what) {
        switch (what)
        {
            case "state":
                return state.getStateJSON();

            case "systemTemp":
                return state.getSystemTemp();

            case "roomTemp":
                return state.getRoomTemp();

            case "systemPower":
                return state.getPower();

            case "acPower":
                return state.getAcPower();
            case "acState":
                return state.getAc();
            case "heatPower":
                return state.getHeatPower();
            case "heatState":
                return state.getHeat();

            case "fanPower":
                return state.getFanPower();
            case "fanState":
                return state.getFan();
            default:
                return what + " not suppored set for HVAC";

        }
    }

    @Override
    public String set(String what, String to) {
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

            case "systemPower":
                if(Objects.equals(to, "on")){
                    setPower(true);
                }
                else if(Objects.equals(to, "off")) {
                    setPower(false);
                }
                else
                {
                    return  to + " not supported for " + what;
                }
                return state.getPower() + "";

            case "acPower":
                if(Objects.equals(to, "on")){
                    setAc(true);
                }
                else if(Objects.equals(to, "off")) {
                    setAc(false);
                }
                else
                {
                    return  to + " not supported for " + what;
                }
                return state.getAcPower() + "";
            case "heatPower":
                if(Objects.equals(to, "on")){
                    setHeat(true);
                }
                else if(Objects.equals(to, "off")) {
                    setHeat(false);
                }
                else
                {
                    return  to + " not supported for " + what;
                }

            case "fanPower":
                if(Objects.equals(to, "on")){
                    setPower(true);
                }
                else if(Objects.equals(to, "off")) {
                    setPower(false);
                }
                else
                {
                    return  to + " not supported for " + what;
                }
            default:
                return what + " not supported set for HVAC";
        }


    }

    @Override
    public String getStateJSON() {
        return null;
    }

    public void update() {
        if(state.getPower()) {
            if( state.getAcPower()) {
                /*
                 * AC Mode
                 */
                if (state.getRoomTemp() > state.getSystemTemp() + THRESHOLD) {
                    state.setAc(true);
                    state.setFan(true);
                }
                /*
                 * Cool down till it gets below the threshold
                 */
                else if (state.getRoomTemp() < state.getRoomTemp() - THRESHOLD) {
                    state.setAc(false);
                    state.setFan(false);
                }
            }
            else if(state.getHeatPower()) {
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
            else if(state.getFanPower()) {
                state.setFan(true);
            }
            else {
                state.setFan(false);
            }
        }

    }

    private void setPower(boolean b)
    {
        //anytime the power changes we want to make sure all things are off
        state.setPower(b);
        state.setFan(false);
        state.setFanPower(false);
        state.setAc(false);
        state.setAcPower(false);
        state.setHeat(false);
        state.setHeatPower(false);
    }
    private void setAc(boolean b)
    {

        state.setHeatPower(false);

        state.setHeat(false);
        state.setAc(false);
        state.setFan(false);

        state.setFanPower(b);
        state.setAcPower(b);

        if(b == false)
        {
            state.setAc(false);
            state.setFan(false);
        }
    }

    private void setHeat(boolean b)
    {
        state.setHeatPower(b);
        state.setFanPower(b);
        state.setAcPower(false);
        state.setAc(false);
        state.setFan(false);
        state.setFan(false);

        if(b == false)
        {
            state.setFanPower(false);
            state.setHeatPower(false);
        }
    }

    private void setFan(boolean b)
    {
        state.setFan(b);
    }
    private void setRoomTemp(double d){ state.setRoomTemp(d);}
    private void setSystemTemp(double d) {
        state.setSystemTemp(d);
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }




}

