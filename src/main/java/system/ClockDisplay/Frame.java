package system.ClockDisplay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Willi on 9/30/2016.
 */
public class Frame {
    //http://www.quinapalus.com/hd44780udg.html

    //Medium Ascii 5x7 Standard Characters
    static private Frame ZERO_FRAME = new Frame(5, 7, new int[]{14, 27, 27, 27, 27, 27, 14});
    static private Frame ONE_FRAME = new Frame(5, 7, new int[]{2,6,14,6,6,6,6});
    static private Frame TWO_FRAME =  new Frame(5, 7, new int[]{14,27,3,6,12,24,31});
    static private Frame THREE_FRAME =   new Frame(5, 7, new int[]{14,27,3,14,3,27,14});
    static private Frame FOUR_FRAME =  new Frame(5, 7, new int[]{3,7,15,27,31,3,3});
    static private Frame FIVE_FRAME =  new Frame(5, 7, new int[]{31,24,30,3,3,27,14});
    static private Frame SIX_FRAME =  new Frame(5, 7, new int[]{14,27,24,30,27,27,14});
    static private Frame SEVEN_FRAME =  new Frame(5, 7, new int[]{31,3,6,12,12,12,12});
    static private Frame EIGHT_FRAME =  new Frame(5, 7, new int[]{14,27,27,14,27,27,14});
    static private Frame NINE_FRAME =  new Frame(5, 7, new int[]{14,27,27,15,3,27,14});
    static protected Frame DEGREE_FRAME =  new Frame(3, 3, new int[]{2,5,2});
    static protected Frame COLON_FRAME = new Frame(5,7, new int[]{0,12,12,0,12,12,0});
    static protected Frame[] NUMBERS_FRAME = new Frame[]{ZERO_FRAME, ONE_FRAME, TWO_FRAME, THREE_FRAME, FOUR_FRAME, FIVE_FRAME, SIX_FRAME, SEVEN_FRAME, EIGHT_FRAME, NINE_FRAME};
    static protected Frame EMPTY_FRAME = new Frame(5,7, new int[]{0,0,0,0,0,0,0});


    //Small ASCII 3 x 5
    static private Frame ZERO_FRAME_SMALL =     new Frame(3, 5, new int[]{7,5,5,5,7});
    static private Frame ONE_FRAME_SMALL =      new Frame(2, 5, new int[]{1,1,1,1,1});
    static private Frame TWO_FRAME_SMALL =      new Frame(3, 5, new int[]{7,1,7,4,7});
    static private Frame THREE_FRAME_SMALL =    new Frame(3, 5, new int[]{7,1,7,1,7});
    static private Frame FOUR_FRAME_SMALL =     new Frame(3, 5, new int[]{5,5,7,1,1});
    static private Frame FIVE_FRAME_SMALL =     new Frame(3, 5, new int[]{7,4,7,1,7});
    static private Frame SIX_FRAME_SMALL =      new Frame(3, 5, new int[]{7,4,7,5,7});
    static private Frame SEVEN_FRAME_SMALL =    new Frame(3, 5, new int[]{7,1,1,1,1});
    static private Frame EIGHT_FRAME_SMALL =    new Frame(3, 5, new int[]{7,5,7,5,7});
    static private Frame NINE_FRAME_SMALL =     new Frame(3,5, new int[]{7,5,7,1,1});
    static protected Frame DEGREE_FRAME_SMALL =   new Frame(1, 1, new int[] {1});
    static protected Frame COLON_FRAME_SMALL =    new Frame(3,5, new int[]{0,2,0,2,0});
    static protected Frame DECIMAL_FRAME_SMALL =  new Frame(1,5,new int[]{0,0,0,0,1});
    static protected Frame[] NUMBERS_FRAME_SMALL = new Frame[]{ZERO_FRAME_SMALL, ONE_FRAME_SMALL, TWO_FRAME_SMALL, THREE_FRAME_SMALL, FOUR_FRAME_SMALL, FIVE_FRAME_SMALL, SIX_FRAME_SMALL, SEVEN_FRAME_SMALL, EIGHT_FRAME_SMALL, NINE_FRAME_SMALL};
    static protected Frame EMPTY_FRAME_SMALL = new Frame(3,5, new int[]{0,0,0,0,0});

    //Large 13x19 ASCII Frames
    static protected Frame ZERO_FRAME_LARGE        = new Frame(13,19, new int[]{1016,2044,4094,7967,7695,7175,7175,7175,7175,7175,7175,7175,7175,7175,7695,7967,4094,2044,1016,});
    static protected Frame ONE_FRAME_LARGE         = new Frame(13,19, new int[]{224,496,1008,2032,4080,240,240,240,240,240,240,240,240,240,240,240,240,240,240,});
    static protected Frame TWO_FRAME_LARGE         = new Frame(13,19, new int[]{504,1020,2046,3855,3079,7,15,30,60,120,240,480,960,1920,3840,7168,7168,8190,4095,});
    static protected Frame THREE_FRAME_LARGE       = new Frame(13,19, new int[]{1016,2044,4094,31,15,7,15,31,510,508,510,31,15,7,15,31,4094,2044,1016,});
    static protected Frame FOUR_FRAME_LARGE        = new Frame(13,19, new int[]{15,31,63,119,231,455,903,1799,3591,7175,8191,4095,7,7,7,7,7,7,7,});
    static protected Frame FIVE_FRAME_LARGE        = new Frame(13,19, new int[]{8191,8191,7680,7680,7680,7680,7680,7680,8190,8191,7,7,7,7,6151,7182,3612,4092,2040,});
    static protected Frame SIX_FRAME_LARGE         = new Frame(13,19, new int[]{2040,4092,8190,7680,7168,7168,7168,7680,8184,8188,8190,7967,7695,7175,7695,7967,4094,2044,1016,});
    static protected Frame SEVEN_FRAME_LARGE       = new Frame(13,19, new int[]{4094,8191,15,7,7,14,30,28,56,56,112,112,224,224,448,448,896,896,896,});
    static protected Frame EIGHT_FRAME_LARGE       = new Frame(13,19, new int[]{1016,2044,4094,7967,7695,7175,7695,7967,4094,2044,4094,7967,7695,7175,7695,7967,4094,2044,1016,});
    static protected Frame NINE_FRAME_LARGE        = new Frame(13,19, new int[]{1016,2044,4094,7967,7695,7175,7695,7967,4095,2047,511,15,7,7,7,7,7,7,7,});
    static protected Frame[] NUMBERS_FRAME_LARGE = new Frame[]{ZERO_FRAME_LARGE, ONE_FRAME_LARGE, TWO_FRAME_LARGE, THREE_FRAME_LARGE, FOUR_FRAME_LARGE, FIVE_FRAME_LARGE, SIX_FRAME_LARGE, SEVEN_FRAME_LARGE, EIGHT_FRAME_LARGE, NINE_FRAME_LARGE};
    static protected Frame COLON_FRAME_LARGE = new Frame(3,19,new int[]{0,0,0,0,0,7,7,7,0,0,0,7,7,7,0,0,0,0,0});
    static protected Frame EMPTY_FRAME_LARGE = new Frame(13,19, new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});


    //Spin1 Motion Frame
    static protected Frame SPIN1_0 = new Frame(5,7, new int[]{27,27,27,0,27,27,27});
    static protected Frame SPIN1_1 = new Frame(5,7, new int[]{3,3,3,0,27,27,27});
    static protected Frame SPIN1_2 = new Frame(5,7, new int[]{24,24,24,0,27,27,27});
    static protected Frame SPIN1_3 = new Frame(5,7, new int[]{27,27,27,0,24,24,24});
    static protected Frame SPIN1_4 = new Frame(5,7, new int[]{27,27,27,0,3,3,3});
    static protected Frame[] SPIN1 = new Frame[]{SPIN1_1,SPIN1_2, SPIN1_3,SPIN1_4};







    protected  String FrameID;
    protected int length, height;
    protected Pixel[][] pixels;
    protected ArrayList<Frame> subFrames;
    protected int frameNumber = -1;

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
        for(int r=0;r<height;r++)
        {
            String line = Integer.toBinaryString(values[r]);
            while(line.length() < length)
                line = '0' + line;
            for(int c=0;c<length;c++)
            {
                if (line.charAt(c) == '1')
                    pixels[r][c] = new Pixel(128,128,128);
                else
                {
                    pixels[r][c] = new Pixel(0,0,0);
                }
            }
        }

    }
    void setFrameNumber(int i)
    {
        frameNumber = i;
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
                if(r+i < getHeight() && c+j < getLength())
                    pixels[r+i][c+j] = f.getPixel(i,j);
            }
        }
    }

    public File writeFrame(String filename)
    {
        BufferedImage image = new BufferedImage(64, 32, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getLength(); j++) {
                image.setRGB(j, i, getPixel(i, j).getRGB());
            }
        }

        File outputFile = new File(filename);
        try {
            ImageIO.write(image, "gif", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }



    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < getHeight();i++)
        {
            for(int j=0;j<getLength();j++)
            {
                if(pixels[i][j].getRed() != 0 || pixels[i][j].getBlue() != 0 || pixels[i][j].getGreen() != 0)
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
