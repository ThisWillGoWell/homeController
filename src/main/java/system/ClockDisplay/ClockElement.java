package system.ClockDisplay;
import controller.Engine;
import org.json.hue.JSONArray;
import org.json.hue.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Willi on 9/30/2016.
 */
public class ClockElement extends DisplayElement {
    SimpleDateFormat format;
    int characterCount;
    static long UPDATE_INTERVAL = 1000;

    ClockElement(String id, SpriteDict spriteDict, int size, int row, int col, SimpleDateFormat format, int characterCount) {
        super(id, spriteDict, size, row, col, UPDATE_INTERVAL);
        this.characterCount = characterCount;
        this.format = format;
    }

    JSONObject get(long time) {
        //make the time for that time
        String timeStr = format.format(new Date(time));
        while (timeStr.length() < characterCount){
            timeStr = " " + timeStr;
        }
        JSONObject json = new JSONObject();
        JSONArray list = new JSONArray();
        int writePointerRow = row;
        int writePointerCol = col;
        for (int i = 0; i < timeStr.length(); i++) {
            JSONObject frameData = new JSONObject();
            Frame s = spriteDict.get(timeStr.charAt(i) + "").getFrames().get(size);
            frameData.put("n", s.frameNumber);
            frameData.put("w", s.length);
            frameData.put("h", s.height);
            frameData.put("r", writePointerRow);
            frameData.put("c", writePointerCol);
            list.put(frameData);
            writePointerCol += s.length + size+1;
        }
        json.put("id", id);
        json.put("t", time);
        json.put("f", list);

        return json;
    }

    public static void main(String[] args) {
        SpriteDict s = new SpriteDict();
        ClockElement c =new ClockElement("clock",s, 0, 0,0,new SimpleDateFormat("h:mm"), 5);
        System.out.println(c.get(System.currentTimeMillis()).toString());


    }
}


