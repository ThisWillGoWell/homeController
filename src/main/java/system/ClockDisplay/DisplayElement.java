package system.ClockDisplay;

import com.google.gson.JsonObject;
import org.json.hue.JSONObject;

import java.util.ArrayList;

/**
 * Parent Object for anything that wishes to be displayed on scrren
 *
 */
public abstract class DisplayElement {

    SpriteDict spriteDict;
    int size, row, col;
    long updateInterval;
    LayerManager layerManager;

    String id;
    DisplayElement(String id, ClockDisplaySystem system, int size, int row, int col, long updateInterval)
    {
        this.id = id;
        this.spriteDict = system.spriteDict;
        layerManager = system.layerManager;
        this.size = size;
        this.row = row;
        this.col =col;
        this.updateInterval = updateInterval;
    }

    abstract JsonObject[] get(long time);

    long getUpdateInterval(){
        return updateInterval;
    }

    void update()
    {

    }

    static JsonObject fill(int r, int g, int b, int a)
    {
        JsonObject json = new JsonObject();
        json.addProperty("fill",true);
        json.addProperty("r", r);
        json.addProperty("g", g);
        json.addProperty("b", b);
        json.addProperty("a", a);

        return json;
    }

    static JsonObject fill()
    {
        JsonObject json = new JsonObject();
        json.addProperty("fill", false);
        return json;
    }


}
