package controller;

import modules.Weather;
import system.network.NetworkSystem;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import system.hvac.HvacSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
@Service
class Engine {

    private HvacSystem systemHVAC;
    private GpioController GPIO;
    private NetworkSystem networkSystem;
    private Weather weather;


    static String timestamp()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\t").format(Calendar.getInstance().getTime());
    }

    public Engine()
    {
        initialize();
    }
    void initialize()
    {
        networkSystem = new NetworkSystem();
        systemHVAC = new HvacSystem();
        weather= new Weather();
    }

    @Scheduled(fixedRate = 5 * 60000)
    public void logWifi()
    {
        System.out.println();
    }

    @Scheduled(fixedRate = 1000)
    public void logSystem(){
        System.out.println(timestamp() + getState());
    }

    @Scheduled(fixedRate = 5000)
    public void updateSystem()
    {
        systemHVAC.update();
    }

    @Scheduled(fixedRate = 5000)
    public void updateCurrentTemp()
    {

    }
    void setRoomTemp(double d){systemHVAC.setRoomTemp(d);}
    void setSystemTemp(double d)
    {
        systemHVAC.setSystemTemp(d);
    }
    void setAc(boolean b)
    {
        systemHVAC.setAc(b);
    }
    void setHeat(boolean b)
    {
        systemHVAC.setHeat(b);
    }
    void setFan(boolean b)
    {
        systemHVAC.setFan(b);
    }
    void setPower(boolean b)
    {
        systemHVAC.setPower(b);
    }

    double getMaxTempToday()
    {
        return weather.getTodayHigh();
    }

    double getMinTempToday()
    {
        return weather.getTodayLow();
    }

    String getForcast()
    {
        return weather.getTodayForcast();
    }



    String getState()
    {
        return "{\"state\":" + networkSystem.getStateJSON() + "," + systemHVAC.getStateJSON() + "}";
    }




}
