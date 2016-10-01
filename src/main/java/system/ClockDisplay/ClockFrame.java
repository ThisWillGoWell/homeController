package system.ClockDisplay;
import controller.Engine;
/**
 * Created by Willi on 9/30/2016.
 */
public class ClockFrame extends Frame{

    ClockFrame()
    {
        super(50,7);


        update();
    }

    void update()
    {
        String timeStr = Engine.time();
        for(int i=0;i<timeStr.length();i++)
        {
            if (Character.isDigit(timeStr.charAt(i)))
            {
                this.placeFrame(0,i*5 +i, Frame.NUMBERS_FRAMES_SMALL[Integer.parseInt(timeStr.charAt(i) + "")]);
            }
            switch (timeStr.charAt(i))
            {
                case ':':
                    this.placeFrame(0,i*5 + i,Frame.COLON_FRAME_SMALL);
                    break;

            }
        }
    }

    private void addNumber()
    {

    }

    public static void main(String[] args)
    {
        ClockFrame clockFrame = new ClockFrame();
        System.out.println(clockFrame);
    }



}
