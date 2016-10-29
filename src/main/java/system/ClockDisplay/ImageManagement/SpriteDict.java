package system.ClockDisplay.ImageManagement;

import java.util.HashMap;

/**
 * Created by Willi on 10/2/2016.
 */
public class SpriteDict extends HashMap<String,Sprite>{
    public SpriteDict()
    {
        super();
        make();
    }

    private void make()
    {
        //Make the complex Frames


        //Numbers
        for (int i = 0; i < 10; i++) {
            this.put(i+"", new Sprite(i+"", new Frame[]{Frame.NUMBERS_FRAME_SMALL[i], Frame.NUMBERS_FRAME[i], Frame.NUMBERS_FRAME_LARGE[i]} ));
        }

        //OtherCharacters
        this.put(" ", new Sprite(" ", new Frame[]{Frame.EMPTY_FRAME_SMALL, Frame.EMPTY_FRAME, Frame.EMPTY_FRAME_LARGE}));
        this.put(":", new Sprite(":", new Frame[]{Frame.COLON_FRAME_SMALL, Frame.COLON_FRAME, Frame.COLON_FRAME_LARGE}));
        this.put("degree", new Sprite("degree", new Frame[]{Frame.DEGREE_FRAME_SMALL, Frame.DEGREE_FRAME}));
        this.put(".", new Sprite(".", new Frame[]{Frame.DECIMAL_FRAME_SMALL, Frame.DECIMAL_FRAME, Frame.DECIMAL_FRAME_LARGE}));
        this.put("spin1", new MotionSprite("spin1",Frame.SPIN1, 500));
        this.put("pixel", new Sprite("pixel", new Frame[]{Frame.PIXEL}));
        this.put("rainDrop", new Sprite("rainDrop", new Frame[]{Frame.RAIN_SCREEN()}));



    }


}