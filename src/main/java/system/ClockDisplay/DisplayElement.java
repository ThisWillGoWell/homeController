package system.ClockDisplay;

import org.json.hue.JSONObject;

import java.util.ArrayList;

/**
 * Created by Willi on 10/2/2016.
 */
public abstract class DisplayElement {

    SpriteDict spriteDict;
    protected int size, row, col;
    long updateInterval;
    String id;
    DisplayElement(String id, SpriteDict spriteDict, int size, int row, int col, long updateInterval)
    {
        this.id = id;
        this.spriteDict = spriteDict;
        this.size = size;
        this.row = row;
        this.col =col;
        this.updateInterval = updateInterval;
    }

    abstract JSONObject get(long time);

    long getUpdateInterval(){
        return updateInterval;
    }

}
