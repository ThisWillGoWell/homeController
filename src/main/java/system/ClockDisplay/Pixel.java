package system.ClockDisplay;

/**
 * Created by Willi on 9/30/2016.
 */
public class Pixel {

    int red;
    int blue;
    int green;

    public Pixel(int red, int blue, int green)
    {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    int getRGB()
    {
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }

    @Override
    public String toString() {
        return "(r: " + red + " g: " + green + " b: " + blue +")";
    }
}
