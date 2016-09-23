package controller;

/**
 * Created by Will on 9/3/2016.
 */
public class SystemState {

    private boolean power;

    private boolean heatPower;
    private boolean acPower;
    private boolean fanPower;

    private boolean heat;
    private boolean ac;
    private boolean fan;

    private double roomTemp;
    private double systemTemp;


    public SystemState()
    {
        power = false;

        fanPower = false;
        heatPower = false;
        acPower = false;

        heat = false;
        ac = false;
        fan = false;

        roomTemp = 27;
        systemTemp = 27;
    }

    public boolean getPower()
    {
        return power;
    }

    public void setPower(boolean b)
    {
        power = b;
    }
    public boolean getHeat()
    {
        return heat;
    }

    public void setHeat(boolean b)
    {
        heat = b;
    }

    public boolean getAc()
    {
        return ac;
    }

    public void setAc(boolean b)
    {
        ac = b;
    }

    public boolean getFan()
    {
        return fan ;
    }

    public void setFan(boolean b)
    {
        fan = b;
    }

    public void setRoomTemp(double temp)
    {
        roomTemp = temp;
    }

    public double getRoomTemp()
    {
        return roomTemp;
    }

    public void setSystemTemp(double d)
    {
        systemTemp = d;
    }

    public double getSystemTemp()
    {
        return systemTemp;
    }

    public void setFanPower(boolean b)
    {
        fanPower = b;
    }

    public boolean getFanPower()
    {
        return  fanPower;
    }

    public void setHeatPower(boolean b)
    {
        heatPower = b;
    }

    public boolean getHeatPower()
    {
        return  heatPower;
    }

    public void setAcPower(boolean b)
    {
        acPower = b;
    }

    public boolean  getAcPower()
    {
        return  acPower;
    }

    public String getStateJSON()
    {
        String s = "{\"settings\": {";

        s += "\"power\" :\"" + power + "\",";
        s += "\"fanPower\" :\"" + fanPower + "\",";
        s += "\"heatPower\" :\"" + heatPower + "\",";
        s += "\"acPower\" :\"" + acPower + "\",";
        s += "\"heat\" :\"" + heat + "\",";
        s += "\"ac\" :\"" + ac + "\",";
        s += "\"fan\" :\"" + fan + "\",";
        s += "\"roomTemp\" :\"" + roomTemp + "\",";
        s += "\"systemTemp\" :\"" + systemTemp + "\"";
        s+="}}";

        return s;

    }


}
