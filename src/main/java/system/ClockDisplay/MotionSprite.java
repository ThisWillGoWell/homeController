package system.ClockDisplay;

import java.util.ArrayList;

/**
 * Created by Willi on 10/2/2016.
 */
public class MotionSprite extends Sprite {

    long updateInterval;
    long startTime;
    int currentIndex;
    long nextUpdateTime;
    int numFrames;


     MotionSprite(String spriteID, ArrayList<Frame> frames, long updateInterval) {
        super(spriteID,frames);

        this.updateInterval = updateInterval;
        this.startTime = System.currentTimeMillis();
        currentIndex = 0;
        numFrames = frames.size();
        animated = true;
    }

    MotionSprite(String spriteID, Frame[] f, long updateInterval) {
        super(spriteID,f);
        this.updateInterval = updateInterval;
        this.startTime = System.currentTimeMillis();
        currentIndex = 0;
        numFrames = this.frames.size();
        animated = true;

    }


    /*
        Given a time, what index should it be at?
     */
    int getFrameIndex(long time)
    {
        return (int) (((System.currentTimeMillis())/updateInterval) % numFrames);
    }

    long getNextUpdateTime()
    {
        return System.currentTimeMillis() + System.currentTimeMillis() % updateInterval;
    }





}
