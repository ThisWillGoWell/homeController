package system.ClockDisplay.DisplayElements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Engine;
import system.ClockDisplay.ClockDisplaySystem;
import system.ClockDisplay.ImageManagement.Frame;

import java.util.ArrayList;

/**
 * Created by Willi on 10/1/2016.
 */

public class WeatherElement extends DisplayElement{

    private Engine e;
    private int numRows;
    private int writePointerRow;
    private int writePointerCol;
    private JsonArray forecast;
    private double currentTemp;
    private long tempUpdateRate;
    private ArrayList<Double> temps;
    private ArrayList<String> conditions = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();
    private double minTemp, maxTemp,tempTemp;
    private  String tempDisplayID = "tempDisplay";
    private  String tempGraphID = "tempGraph";

    private JsonObject graphJson;
    private JsonObject currentTempJson;
    private JsonObject maxTempJson;
    private JsonObject minTempJson;

    public WeatherElement(String id, ClockDisplaySystem clockDisplaySystem, int size, int row, int col, long updateInterval, Engine e){
        super(id, clockDisplaySystem,size, row, col, updateInterval);
        this.e = e;
        numRows = size;
        writePointerRow = row;
        writePointerCol = col;
        tempUpdateRate = 5000;
        temps = new ArrayList<>();
        conditions = new ArrayList<>();
        times = new ArrayList<>();
        tempDisplayID = id + "-"+tempDisplayID;
        tempGraphID = id + "-"+tempGraphID;
        layerManager.addLayer(tempDisplayID);
        layerManager.addLayer(tempGraphID);

        graphJson = new JsonObject();
        graphJson.addProperty("id", tempGraphID);
        graphJson.add("fill", fill());
        graphJson.addProperty("l", layerManager.get(tempGraphID));

        currentTempJson = new JsonObject();
        currentTempJson.addProperty("id", tempDisplayID);
        currentTempJson.add("fill", fill(255,255,255,255));
        currentTempJson.addProperty("l", layerManager.get(tempDisplayID));

        maxTempJson = new JsonObject();
        maxTempJson.addProperty("id", tempDisplayID);
        maxTempJson.add("fill", fill(255,0,0,255));
        maxTempJson.addProperty("l", layerManager.get(tempDisplayID));

        minTempJson = new JsonObject();
        minTempJson.addProperty("id", tempDisplayID);
        minTempJson.add("fill", fill(0,0,255,255));
        minTempJson.addProperty("l", layerManager.get(tempDisplayID));
    }

    private void updateGraphElement()
    {
        //have access to hourly data
        //~40 pixels
        //get the most recent forecast from the weather subsystem
        //we have all the data now we need to compute the graph.
        //for this one, lets say the
        double tempPerPixel = numRows / (maxTemp - minTemp);
        JsonArray graph = new JsonArray();
        JsonObject currentPoint;
        for(int i = 0;i<temps.size();i++)
        {
            currentPoint = new JsonObject();
            currentPoint.addProperty("r",Math.round(writePointerRow + tempPerPixel * (maxTemp - temps.get(i))));
            currentPoint.addProperty("c",writePointerCol);
            currentPoint.addProperty("n",spriteDict.get("pixel").getFrames().get(0).getFrameNumber());
            currentPoint.addProperty("w",spriteDict.get("pixel").getFrames().get(0).getLength());
            currentPoint.addProperty("h",spriteDict.get("pixel").getFrames().get(0).getHeight());
            writePointerCol += 1;
            graph.add(currentPoint);
        }
        graphJson.add("f",graph);
    }

    private void updateTempElement()
    {
        double[] temps = new double[]{currentTemp,minTemp, maxTemp};
        JsonObject[] jsons = new JsonObject[]{currentTempJson, minTempJson,maxTempJson};
        int tempWriter = writePointerCol;
        for(int j=0;j<3;j++)        {
            String tempString = ((int) temps[j]) + "";
            while(tempString.length() < 3){
                tempString = " " + tempString;
            }
            tempString = tempString.substring(0,2);
            tempWriter = writePointerCol;
            JsonArray frames = new JsonArray();
            Frame s = null;
            for (int i = 0; i < tempString.length(); i++) {
                JsonObject frameData = new JsonObject();
                s = spriteDict.get(tempString.charAt(i) + "").getFrames().get(1);
                frameData.addProperty("n", s.getFrameNumber());
                frameData.addProperty("w", s.getHeight());
                frameData.addProperty("h", s.getHeight());
                frameData.addProperty("r", writePointerRow);
                frameData.addProperty("c", tempWriter);
                frames.add(frameData);
                tempWriter += s.getLength();
            }
            JsonObject frameData = new JsonObject();
            s = spriteDict.get("degree").getFrames().get(1);
            frameData.addProperty("n", s.getFrameNumber());
            frameData.addProperty("w", s.getLength());
            frameData.addProperty("h", s.getHeight());
            frameData.addProperty("r", writePointerRow);
            frameData.addProperty("c", tempWriter);
            frames.add(frameData);
            tempWriter += s.getLength()+ 1;
            jsons[j].add("f",frames);
        }
        writePointerCol = tempWriter;
    }

    @Override
    public JsonObject[] get(long time) {
        //so the update time is going to be the same time as the update interval of the weather object.
        //but bascially I think I want it to be quicker than that..... or do I?

        if(time / updateInterval % 10 == 0)
            return new JsonObject[]{minTempJson, graphJson};
        else if (time/updateInterval%10==5)
            return new JsonObject[]{maxTempJson, graphJson};
        else
            return new JsonObject[]{currentTempJson,graphJson};

    }

    @Override
    public void update()
    {
        forecast = ((JsonObject) e.get("weather","hourlyForecast", null)).getAsJsonArray("hourly_forecast");
        currentTemp = (Double) e.get("weather", "currentTemp", null);
;
        minTemp = maxTemp= currentTemp;
        writePointerRow = row;
        writePointerCol = col;

        temps = new ArrayList<>();
        conditions = new ArrayList<>();
        times = new ArrayList<>();

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
        updateTempElement();
        updateGraphElement();
    }



}
