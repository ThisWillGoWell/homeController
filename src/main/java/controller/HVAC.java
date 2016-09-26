package controller;

/**
 * Created by Will on 9/3/2016.
 */

public class HVAC{


    private SystemState state;
    private double THRESHOLD = 0.75;

    HVAC()
    {
        state = new SystemState();
    }

    void update() {
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

    void setPower(boolean b)
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
    void setAc(boolean b)
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

    void setHeat(boolean b)
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

    void setFan(boolean b)
    {
        state.setFan(b);
    }
    String getStateJSON()
    {
        return state.getStateJSON();
    }
    void setRoomTemp(double d){ state.setRoomTemp(d);}
    void setSystemTemp(double d) {
        state.setSystemTemp(d);
    }
    public SystemState getState() {
        return state;
    }

}

