package controller;

/**
 * Created by Will on 9/3/2016.
 */

public class HVAC{


    private SystemState state;
    HVAC()
    {
        state = new SystemState();
    }

    public void update() {
        if(state.getPower()) {
            if( state.getAcPower()) {
                if (state.getRoomTemp() > state.getSystemTemp()) {
                    state.setAc(true);
                    state.setFan(true);
                }
                else
                {
                    state.setAc(false);
                    state.setFan(false);
                }

            }
            else if(state.getHeatPower())
            {
                 if(state.getRoomTemp() < state.getSystemTemp())
                {
                    state.setHeat(true);
                    state.setFan(false);
                }

            }
            else if(state.getFanPower())
            {

            }
            else
            {

            }

        }

    }

    public void setRoomTemp(double d){ state.setRoomTemp(d);}

    public void setSystemTemp(double d) {
        state.setSystemTemp(d);
    }

    public SystemState getState() {
        return state;
    }

    public void setPower(boolean b)
    {
        //anytime the power changes we want to make sure allthings are off
        state.setPower(b);
        state.setFan(false);
        state.setAc(false);
        state.setHeat(false);

    }
    public void setAc(boolean b)
    {

        state.setHeatPower(false);
        state.setFanPower(b);
        state.setAcPower(b);

        if(b == false)
        {
            state.setAc(false);
            state.setFan(false);
        }
    }

    public void setHeat(boolean b)
    {
        state.setHeatPower(b);
        state.setFanPower(b);
        state.setAcPower(false);

        if(b == false)
        {
            state.setFanPower(false);
            state.setHeatPower(false);
        }
    }

    public void setFan(boolean b)
    {
        state.setFan(b);
    }

    public String getStateJSON()
    {
        return state.getStateJSON();
    }



}

