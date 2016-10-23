package system.ClockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Engine;
import system.Weather.Weather;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Willi on 10/1/2016.
 */

class WeatherElement extends DisplayElement{

    Engine e;
    Weather w;
    int numRows;

    WeatherElement(String id, SpriteDict spriteDict, int size, int row, int col, long updateInterval, Engine e){
        super(id, spriteDict,size, row, col, updateInterval);
        this.e = e;
        numRows = size;
    }


    JsonArray generateWeatherGraph()
    {
        //have access to hourly data
        //~40 pixels
        //get the most recent forecast from the weather subsystem
        JsonArray forecast = ((JsonObject) e.get("weather","hourlyForecast", null)).getAsJsonArray("hourly_forecast");
        ArrayList<Double> temps = new ArrayList<>();
        ArrayList<String> conditions = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();
        double minTemp, maxTemp,tempTemp;
        minTemp = maxTemp= (Double) e.get("weather", "currentTemp", null);
        for(int i=0;i<forecast.size();i++)
        {
            //hourly_forecast[] -> FCTTIME -> epoch
            //hourly_forecast[] -> temp -> metric
            //hourly_forecast[] -> condition

            tempTemp = forecast.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("metric").getAsDouble();

            if(minTemp > tempTemp){
                minTemp = tempTemp;
            }
            else if(maxTemp < tempTemp){
                maxTemp = tempTemp;
            }
            times.add(forecast.get(i).getAsJsonObject().get("FCTTIME").getAsJsonObject().get("epoch").getAsLong());
            temps.add(tempTemp);
            conditions.add(forecast.get(i).getAsJsonObject().get("condition").getAsString());
        }

        //we have all the data now we need to compute the graph.
        //for this one, lets say the
        double tempPerPixel = (maxTemp - minTemp)/numRows;
        JsonArray graph = new JsonArray();
        JsonObject currentPoint;
        int writePointerRow = row;
        int writePointerCol = col;
        String tempString = "" + temps.get(0);


        for (int i = 0; i < tempString.length(); i++) {
            JsonObject frameData = new JsonObject();
            Frame s = spriteDict.get(tempString.charAt(i) + "").getFrames().get(1);
            frameData.addProperty("n", s.frameNumber);
            frameData.addProperty("w", s.length);
            frameData.addProperty("h", s.height);
            frameData.addProperty("r", writePointerRow);
            frameData.addProperty("c", writePointerCol);
            graph.add(frameData);
            writePointerCol += s.length + 1;
        }

        JsonObject frameData = new JsonObject();
        Frame s = spriteDict.get("degree").getFrames().get(1);
        frameData.addProperty("n", s.frameNumber);
        frameData.addProperty("w", s.length);
        frameData.addProperty("h", s.height);
        frameData.addProperty("r", writePointerRow);
        frameData.addProperty("c", writePointerCol);
        graph.add(frameData);
        writePointerCol += s.length + 1;

        for(int i = 0;i<temps.size();i++)
        {
            currentPoint = new JsonObject();
            currentPoint.addProperty("r",Math.round(writePointerRow + tempPerPixel * (maxTemp - temps.get(i))));
            currentPoint.addProperty("c",writePointerCol);
            currentPoint.addProperty("n",spriteDict.get("pixel").getFrames().get(0).frameNumber);
            currentPoint.addProperty("w",spriteDict.get("pixel").getFrames().get(0).length);
            currentPoint.addProperty("h",spriteDict.get("pixel").getFrames().get(0).height);
            writePointerCol += 1;
            graph.add(currentPoint);
        }

        return graph;
    }

    @Override
    JsonObject get(long time) {
        //so the update time is going to be the same time as the update interval of the weather object.
        //but bascially I think I want it to be quicker than that..... or do I?
        JsonArray frames = generateWeatherGraph();
        JsonObject json = new JsonObject();
        json.addProperty("id", "weather");
        json.addProperty("t", time);
        json.add("f", frames);



        return json;
    }

    public static void main(String args)
    {
        WeatherElement we = new WeatherElement("weather",new SpriteDict(), 10,0,0,100, new Engine());
        we.generateWeatherGraph();

    }


}
