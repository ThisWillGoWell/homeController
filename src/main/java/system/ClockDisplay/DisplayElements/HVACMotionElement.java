package system.ClockDisplay.DisplayElements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import system.ClockDisplay.ClockDisplaySystem;
import system.ClockDisplay.ImageManagement.Frame;

import java.util.ArrayList;

/**
 * Created by Willi on 10/28/2016.
 */
public class HVACMotionElement extends DisplayElement {

    /**
     * HVAC Motion Element
     * extends Display Element
     * @param id Unique ID
     * @param system What ClockDisplaySystem does it Belong to?
     * @param row What row does the 0,0 pixel get drawn into
     * @param col What Col does the 0,0 pixel get drawn into
     *
     */

    private ArrayList<Frame> f;
    private long currentRate;
    private JsonObject currentFill;
    private long slowUpdateRate;
    public HVACMotionElement(String id, ClockDisplaySystem system, int row, int col) {
        super(id, system, 0, row, col, 500);
        f = spriteDict.get("spin1").getFrames();
        currentRate = updateInterval;
        slowUpdateRate = updateInterval * 2;
        currentFill = fill();
        layerManager.addLayer(id);
    }

    @Override
    public void update() {
        JsonObject hvacState = (JsonObject) engine.get("HVAC","state", null);
        switch (hvacState.get("mode").getAsString()){
            case "off":
                currentFill = fill();
                currentRate = slowUpdateRate;
                break;
            case "cool":
                currentFill = fill(0,0,255,255);
                if(hvacState.get("acState").getAsBoolean())
                    currentRate = updateInterval;
                else
                    currentRate = slowUpdateRate;
                break;
            case "heat":
                currentFill = fill(255,0,0,255);
                if(hvacState.get("heatState").getAsBoolean())
                    currentRate = updateInterval;
                else
                    currentRate = slowUpdateRate;

                break;
            case "fan":
                currentFill = fill(0,255,0,255);
                if(hvacState.get("fanState").getAsBoolean())
                    currentRate = updateInterval;
                else
                    currentRate = slowUpdateRate;
                break;
        }
    }

    @Override
    public JsonObject[] get(long time) {
        Frame currentFrame = f.get((int) ((time/currentRate )% f.size()));

        JsonObject json = new JsonObject();
        JsonArray ja = new JsonArray();
        JsonObject frameJson = new JsonObject();

        frameJson.addProperty("n", currentFrame.getFrameNumber());
        frameJson.addProperty("w", currentFrame.getLength());
        frameJson.addProperty("h", currentFrame.getHeight());
        frameJson.addProperty("r", row);
        frameJson.addProperty("c", col);

        ja.add(frameJson);
        json.addProperty("id", id);
        json.add("f",ja);
        json.add("fill", currentFill);
        json.addProperty("l",layerManager.get(id));

        return new JsonObject[]{json};
    }
}
