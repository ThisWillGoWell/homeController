package system.hvac;

/**
 * Created by Will on 9/3/2016.
 */

public class HvacSystem {


    private HvacSystemState state;
    private double THRESHOLD = 0.75;

    public HvacSystem()
    {
        state = new HvacSystemState();
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

    public void setPower(boolean b)
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
    public void setAc(boolean b)
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

    public void setHeat(boolean b)
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

    public void setFan(boolean b)
    {
        state.setFan(b);
    }
    public String getStateJSON()
    {
        return state.getStateJSON();
    }
    public void setRoomTemp(double d){ state.setRoomTemp(d);}
    public void setSystemTemp(double d) {
        state.setSystemTemp(d);
    }
    public HvacSystemState getState() {
        return state;
    }

}

