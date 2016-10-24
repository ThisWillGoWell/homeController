package system.ClockDisplay;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Clock;

/**
 * Simple clock element, displays the time, thats it...
 *
 */
public class ClockElement extends DisplayElement {
    SimpleDateFormat format;
    int characterCount;
    static long UPDATE_INTERVAL = 30000;

    ClockElement(String id, ClockDisplaySystem system, int size, int row, int col, SimpleDateFormat format, int characterCount) {
        super(id, system, size, row, col, UPDATE_INTERVAL);
        this.characterCount = characterCount;
        this.format = format;
        this.layerManager.addLayer(id);

    }

    JsonObject[] get(long time) {
        //make the time for that time
        String timeStr = format.format(new Date(time));
        while (timeStr.length() < characterCount) {
            timeStr = " " + timeStr;
        }
        JsonObject json = new JsonObject();
        JsonArray list = new JsonArray();
        int writePointerRow = row;
        int writePointerCol = col;
        for (int i = 0; i < timeStr.length(); i++) {
            JsonObject frameData = new JsonObject();
            Frame s = spriteDict.get(timeStr.charAt(i) + "").getFrames().get(size);
            frameData.addProperty("n", s.frameNumber);
            frameData.addProperty("w", s.length);
            frameData.addProperty("h", s.height);
            frameData.addProperty("r", writePointerRow);
            frameData.addProperty("c", writePointerCol);
            list.add(frameData);
            writePointerCol += s.length + 1;
        }
        json.addProperty("id", id);
        json.addProperty("t", time);
        json.add("f", list);
        json.add("fill",fill());
        json.addProperty("l",layerManager.get(id));

        return new JsonObject[]{json};
    }

}


