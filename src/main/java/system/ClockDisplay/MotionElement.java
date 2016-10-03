package system.ClockDisplay;

import org.json.hue.JSONArray;
import org.json.hue.JSONObject;

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
    JSONObject get(long time) {
        JSONObject json = new JSONObject();
        Frame f = motionSprite.getFrames().get(motionSprite.getFrameIndex(time));
        JSONArray list = new JSONArray();
        JSONObject frame = new JSONObject();
        frame.put("n", f.frameNumber);
        frame.put("w", f.getLength());
        frame.put("h", f.getHeight());
        frame.put("r", row);
        frame.put("c", col);

        list.put(frame);
        json.put("f", list);
        json.put("id", id);
        json.put("t", time);

        return json;
    }


}
