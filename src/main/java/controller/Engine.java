package main.java.controller;

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
        GPIO = new GpioController();
    }

    //upate once evry 10 sec
    @Scheduled(fixedRate = 10000)
    public void updateHVAC()
    {
        systemHVAC.setCurrentTemp(GPIO.getCurrentTemp());
        systemHVAC.update();
    }

    @Scheduled(fixedRate = 5000)
    public void updateCurrentTemp()
    {

    }


    public void setTemp(double d)
    {
        systemHVAC.setCurrentTemp(d);
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
