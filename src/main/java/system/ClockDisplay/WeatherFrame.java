package system.ClockDisplay;

import controller.Engine;
import modules.Weather;

/**
 * Created by Willi on 10/1/2016.
 */
public class WeatherFrame extends Frame{


    Weather weather;

    public WeatherFrame(Weather weather) {
        super(30, 7);
        this.weather = weather;
        update();
    }

    void update()
    {
        String weatherStr = weather.getCurrentTemp() + "" ;
        int writeColPointer = 0;

        for(int i=0;i<weatherStr.length();i++)
        {
            Frame frame = null;
            if (Character.isDigit(weatherStr.charAt(i)))
            {
                frame =  Frame.NUMBERS_FRAME[Integer.parseInt(weatherStr.charAt(i) + "")];
            }
            switch (weatherStr.charAt(i))
            {
                case '.':
                    frame = Frame.DECIMAL_FRAME_SMALL;
                    break;

            }

            placeFrame(0,writeColPointer,frame);
            writeColPointer += frame.getLength() + 1;
        }
        placeFrame(0,writeColPointer, Frame.DEGREE_FRAME);

    }

}
