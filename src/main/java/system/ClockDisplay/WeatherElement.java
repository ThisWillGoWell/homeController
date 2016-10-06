package system.ClockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Engine;
import modules.Weather;

/**
 * Created by Willi on 10/1/2016.
 */
public class WeatherElement extends DisplayElement{

    Engine e;
    WeatherElement(String id, SpriteDict spriteDict, int size, int row, int col, long updateInterval, Engine e){
        super(id, spriteDict,size, row, col, updateInterval);
        this.e = e;

    }




    @Override
    JsonObject get(long time) {
        //Dont care about time intervals,
        //Just give it whatever we have
        //Easy

        JsonObject json = new JsonObject();
        json.addProperty("id", "weather");


        return null;
    }
}
