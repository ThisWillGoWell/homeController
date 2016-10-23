package system.ClockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.hue.JSONArray;


/**
 * Created by Willi on 10/3/2016.
 */
public class MotionElement extends DisplayElement{

    MotionSprite motionSprite;
    MotionElement(String id, SpriteDict spriteDict, int row, int col)
    {
        super( id,  spriteDict,1, row, col, ((MotionSprite) spriteDict.get(id)).updateInterval);
        motionSprite = (MotionSprite) spriteDict.get(id);
    }

    @Override
    JsonObject get(long time) {
        JsonObject json = new JsonObject();
        Frame f = motionSprite.getFrames().get(motionSprite.getFrameIndex(time));
        JsonArray list = new JsonArray();
        JsonObject frame = new JsonObject();
        frame.addProperty("n", f.frameNumber);
        frame.addProperty("w", f.getLength());
        frame.addProperty("h", f.getHeight());
        frame.addProperty("r", row);
        frame.addProperty("c", col);

        list.add(frame);
        json.addProperty("id", id);
        json.addProperty("t", time);
        json.add("f", list);


        return json;
    }


}
