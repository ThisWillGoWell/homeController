package controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Will on 9/3/2016.
 * Engine of the program, highest level.
 * Starts evryhting up on boot, and controls the timeing of the check chav cotnrol
 */
@Service
class Engine {

    HVAC systemHVAC;
    GpioController GPIO;

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

        systemHVAC = new HVAC();
    }


    @Scheduled(fixedRate = 60000)
    public void log(){
        System.out.println(timestamp() + systemHVAC.getStateJSON());
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
    String getState()
    {
        return systemHVAC.getStateJSON();
    }




}
