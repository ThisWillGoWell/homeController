package system.ClockDisplay;

import modules.Weather;
import system.SystemParent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Willi on 9/29/2016.
 */
public class ClockDisplaySystem extends SystemParent{

   /*
   So get request is going to just get the rgb values for what the clock needs to display
   Right now the sever and the clock are going to be running on the same lcoalhost so maybe liek 5 times a second update()

    */

   Frame masterFrame;
    Weather weather;
    public ClockDisplaySystem(Weather weather)
    {
        this.weather = weather;
        masterFrame = new Frame(64,32);
    }

    @Override
    public String getStateJSON() {
        return "";
    }

    public File writeFrame()
    {
        return  masterFrame.writeFrame("frame.gif");
    }

    public void update()
    {
        masterFrame.placeFrame(0,1,new ClockFrame(2));
        masterFrame.placeFrame(25,5, new WeatherFrame(weather));
    }

    public static void main(String args[]) {
        ClockDisplaySystem system = new ClockDisplaySystem(new Weather());
        Frame clockFrame = new ClockFrame(2);

        BufferedImage image = new BufferedImage(64, 32, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < clockFrame.getHeight(); i++) {
            for (int j = 0; j < clockFrame.getLength(); j++) {
                image.setRGB(j, i, clockFrame.getPixel(i, j).getRGB());
            }
        }

        File outputFile = new File("testImage.gif");
        try {
            ImageIO.write(image, "gif", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
