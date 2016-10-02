package system.ClockDisplay;
import controller.Engine;

/**
 * Created by Willi on 9/30/2016.
 */
public class ClockFrame extends Frame{

    private Frame[] frameNumberList;
    private Frame colonFrame, emptyFrame;
    private int size;
    ClockFrame(int size)
    {

        super(70,32);
        this.size =size;
        if (size == 0){ //Small Numbers
            frameNumberList = Frame.NUMBERS_FRAME_SMALL ;
            colonFrame = Frame.COLON_FRAME_SMALL;
            length = 25;
            height = 5;
            emptyFrame = Frame.EMPTY_FRAME_SMALL;
        }
        else if(size == 1){
            frameNumberList = Frame.NUMBERS_FRAME;
            colonFrame = Frame.COLON_FRAME;
            length = 30;
            height = 7;
            emptyFrame = Frame.EMPTY_FRAME;

        }
        else if(size == 2) {
            length = 70;
            height = 19;
            frameNumberList = Frame.NUMBERS_FRAME_LARGE;
            colonFrame = Frame.COLON_FRAME_LARGE;
            emptyFrame = Frame.EMPTY_FRAME_LARGE;
        }
        update();
    }

    void update()
    {
        String timeStr = Engine.time();
        if(timeStr.length() == 4)
        {
            timeStr = " " + timeStr;
        }
        int writeColPointer = 0;
        for(int i=0;i<timeStr.length();i++)
        {
            Frame frame = null;
            if (Character.isDigit(timeStr.charAt(i)))
            {
                frame = frameNumberList[Integer.parseInt(timeStr.charAt(i) + "")];
            }
            else{
                switch (timeStr.charAt(i))
                {
                    case ' ':
                        frame = emptyFrame;
                        break;
                    case ':':
                        frame = colonFrame;
                        break;
                }
            }
            placeFrame(0,writeColPointer, frame);
            writeColPointer+= frame.getLength() + size;

        }
        Frame frame = null;
    }



    public static void main(String[] args)
    {
        ClockFrame clockFrame = new ClockFrame(0);
        System.out.println(clockFrame);
    }



}
