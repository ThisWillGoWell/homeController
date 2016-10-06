package system.ClockDisplay;

import com.google.gson.JsonObject;
import org.json.hue.JSONObject;

import java.util.ArrayList;

/**
 * Created by Willi on 9/29/2016.
 */
public class ClockDisplayState {

    ArrayList<DisplayElement> elements;
    ClockDisplayState(ArrayList<DisplayElement> e)
    {
        elements = e;
    }

    JsonObject getStateJSON()
    {
        JsonObject state = new JsonObject();
        for(DisplayElement e: elements){

        }
        return state;
    }




}
