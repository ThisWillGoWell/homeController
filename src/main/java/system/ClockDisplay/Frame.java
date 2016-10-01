package system.ClockDisplay;

import java.util.ArrayList;

/**
 * Created by Willi on 9/30/2016.
 */
public class Frame {
    //http://www.quinapalus.com/hd44780udg.html

    //Small Ascii 5x7 Standard Characters
    static Frame ZERO_FRAME_SMALL = new Frame(5, 7, new int[]{14, 27, 27, 27, 27, 27, 14});
    static Frame ONE_FRAME_SMALL = new Frame(5, 7, new int[]{2,6,14,6,6,6,6});
    static Frame TWO_FRAME_SMALL =  new Frame(5, 7, new int[]{14,27,3,6,12,24,31});
    static Frame THREE_FRAME_SMALL =   new Frame(5, 7, new int[]{14,27,3,14,3,27,14});
    static Frame FOUR_FRAME_SMALL =  new Frame(5, 7, new int[]{3,7,15,27,31,3,3});
    static Frame FIVE_FRAME_SMALL =  new Frame(5, 7, new int[]{31,24,30,3,3,27,14});
    static Frame SIX_FRAME_SMALL =  new Frame(5, 7, new int[]{14,27,24,30,27,27,14});
    static Frame SEVEN_FRAME_SMALL =  new Frame(5, 7, new int[]{31,3,6,12,12,12,12});
    static Frame EIGHT_FRAME_SMALL =  new Frame(5, 7, new int[]{14,27,27,14,27,27,14});
    static Frame NINE_FRAME_SMALL =  new Frame(5, 7, new int[]{14,27,27,15,3,27,14});
    static Frame DEGREE_FRAME_SMALL =  new Frame(3, 3, new int[]{4,5,4});
    static Frame COLON_FRAME_SMALL = new Frame(5,7, new int[]{0,0,4,0,4,0,0});
    static Frame[] NUMBERS_FRAMES_SMALL = new Frame[]{ZERO_FRAME_SMALL, ONE_FRAME_SMALL, TWO_FRAME_SMALL, THREE_FRAME_SMALL, FOUR_FRAME_SMALL, FIVE_FRAME_SMALL, SIX_FRAME_SMALL, SEVEN_FRAME_SMALL, EIGHT_FRAME_SMALL, NINE_FRAME_SMALL};

    //Medium ASCII 8 x 16
    static Frame zero = null;
    static Frame one = null;
    static Frame two = null;
    static Frame three=  null;
    static Frame four = null;
    static Frame five = null;
    static Frame six = null;
    static Frame seven = null;
    static Frame eight = null;
    static Frame nine = null;
    static Frame colon = null;

    //Large Ascii
    static Frame zeroLarge = null;
    static Frame oneLarge = null;
    static Frame twoLarge = null;
    static Frame threeLarge=  null;
    static Frame fourLarge = null;
    static Frame fiveLarge = null;
    static Frame sixLarge = null;
    static Frame sevenLarge = null;
    static Frame eightLarge = null;
    static Frame nineLarge = null;
    static Frame colonLarge = null;


    protected int length, height;
    protected Pixel[][] pixels;
    protected ArrayList<Frame> subFrames;

    public Frame(int length,int height)
    {
        pixels = new Pixel[height][length];
        subFrames = new ArrayList<Frame>();
        this.length = length;
        this.height = height;

        for(int r=0;r<height;r++)
        {
            for(int c= 0;c<length;c++)
            {
                pixels[r][c] = new Pixel(0,0,0);
            }
        }
    }

    public Frame(int length, int height, int[] values)
    {
        subFrames = new ArrayList<Frame>();
        pixels = new Pixel[height][length];
        this.length = length;
        this.height = height;
        for(int r=0;r<values.length;r++)
        {
            String line = Integer.toBinaryString(values[r]);
            while(line.length() < length)
                line = '0' + line;
            for(int c=0;c<line.length();c++)
            {
                if (line.charAt(c) == '1')
                    pixels[r][c] = new Pixel(255,255,255);
                else
                {
                    pixels[r][c] = new Pixel(0,0,0);
                }
            }
        }

    }

    Pixel[][] getPixels()
    {
        return pixels;
    }
    Pixel getPixel(int r, int c)
    {
        return pixels[r][c];
    }

    int getLength()
    {
        return length;
    }

    int getHeight()
    {
        return height;
    }


    void placeFrame(int r, int c, Frame f) {
        //subFrames.add(f);
        for (int i = 0; i < f.getHeight();i++)
        {
            for(int j=0;j<f.getLength();j++)
            {
                pixels[r+i][c+j] = f.getPixel(i,j);
            }
        }
    }

    void writeFrame(String filename)
    {

    }



    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < getHeight();i++)
        {
            for(int j=0;j<getLength();j++)
            {
                if(pixels[i][j].red != 0 || pixels[i][j].blue != 0 || pixels[i][j].green != 0)
                    s+='1';
                else
                    s+=' ';
            }
            s+="\n";
        }
        return s;
    }

    public static void main(String args[])
    {
        System.out.println("test bby");
    }











}
