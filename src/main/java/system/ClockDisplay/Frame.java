package system.ClockDisplay;

import java.util.ArrayList;

/**
 * Created by Willi on 9/30/2016.
 */
public class Frame {
    //http://www.quinapalus.com/hd44780udg.html

    //Small Ascii 5x8 Standard Characters
    static Frame zeroSmall = null;
    static Frame oneSmall = null;
    static Frame twoSmall = null;
    static Frame threeSmall=  null;
    static Frame fourSmall = null;
    static Frame fiveSmall = null;
    static Frame sixSmall = null;
    static Frame sevenSmall = null;
    static Frame eightSmall = null;
    static Frame nineSmall = null;
    static Frame degreeSmall = null;

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


    public Frame(int length,int height)
    {

    }

    public Frame(int[] values)
    {

    }

    public Frame()
    {

    }

    private ArrayList<Frame> subFrames;



}
