package system.ClockDisplay;

import com.google.gson.JsonObject;
import org.json.hue.JSONObject;

import java.util.ArrayList;

/**
 * Not really sure what this does yet?
 * @TODO Figure out if this does anything
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
