package system.hue;

/**
 * Created by Willi on 11/17/2016.
 */
public class Switch {

    private boolean state;
    Switch()
    {
        state = false;
    }

    void setState(boolean b){
        state = b;
    }

    boolean getState(boolean b)
    {
        return state;
    }



}
