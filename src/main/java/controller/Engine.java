package controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
@Service
public class Engine {

    HVAC systemHVAC;
    GpioController GPIO;


    public Engine()
    {
        initialize();
    }
    public void initialize()
    {

        systemHVAC = new HVAC();
    }



    @Scheduled(fixedRate = 5000)
    public void updateCurrentTemp()
    {

    }


    public void setRoomTemp(double d){systemHVAC.setRoomTemp(d);}

    public void setSystemTemp(double d)
    {
        systemHVAC.setSystemTemp(d);
    }

    public void setAc(boolean b)
    {
        systemHVAC.setAc(b);
    }

    public void setHeat(boolean b)
    {
        systemHVAC.setHeat(b);
    }

    public void setFan(boolean b)
    {
        systemHVAC.setFan(b);
    }
    public void setPower(boolean b)
    {
        systemHVAC.setPower(b);
    }
    public String getState()
    {
        return systemHVAC.getStateJSON();
    }




}
