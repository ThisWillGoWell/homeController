package system.ClockDisplay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import controller.Engine;
import system.Weather.Weather;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import system.SystemParent;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

    File ResouceGif;

    int rows = 32;
    int cols = 64;
    SpriteDict spriteDict;
    ClockElement clock;
    ArrayList<DisplayElement> elements = new ArrayList<>();
    File resouceGif;
    public ClockDisplaySystem(Engine e)
    {
        super(e);
        spriteDict = new SpriteDict();
        elements.add(new ClockElement("clock", spriteDict, 2,0,0,new SimpleDateFormat("h:mm"), 5));
        elements.add(new MotionElement("spin1", spriteDict, 23, 58));
        try {
            writeResourceGif();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @Override
    public Object get(String what, Map<String, String> requestParams) {
        if(Objects.equals(what, "resourceImage")){
            try {
                return getResourceGif();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(what,"imageStart"))
        {
            if((requestParams.containsKey("t1") && Engine.isNumeric(requestParams.get("t1"))) &&
                    (requestParams.containsKey("t2") && Engine.isNumeric(requestParams.get("t2"))) &&
                    (requestParams.containsKey("interval") && Engine.isNumeric(requestParams.get("interval"))))
            {
                return getImageUpdate(Long.parseLong(requestParams.get("t1")), Long.parseLong(requestParams.get("t2")),
                        Integer.parseInt(requestParams.get("interval")), true);
            }
            else
            {
                return(what + " requires long param t1 and t2");
            }
        }

        else if(Objects.equals(what, "imageUpdate"))
        {
            if((requestParams.containsKey("t1") && Engine.isNumeric(requestParams.get("t1"))) &&
                    (requestParams.containsKey("t2") && Engine.isNumeric(requestParams.get("t2"))) &&
                    (requestParams.containsKey("interval") && Engine.isNumeric(requestParams.get("interval"))))
            {
                return getImageUpdate(Long.parseLong(requestParams.get("t1")), Long.parseLong(requestParams.get("t2")),
                        Integer.parseInt(requestParams.get("interval")), false);
            }
            else
            {
                return(what + " requires long param t1 and t2");
            }
        }




        return null;
    }


    private String getImageUpdate(long start, long stop, long interval, boolean fullImage)
    {

        JsonObject imageUpdate = new JsonObject();
        JsonArray frames = new JsonArray();

        for(long i=start; i<stop; i+=interval)
        {
            JsonObject time = new JsonObject();
            JsonArray eles = new JsonArray();
            for (DisplayElement e: elements) {
                if(fullImage && i==start) {
                    eles.add(e.get(i));
                }
                else if(i%e.getUpdateInterval() < interval){
                    eles.add(e.get(i));
                }
            }
            if(eles.size() != 0) {
                time.add("elements", eles);
                time.addProperty("time", i);
                frames.add(time);
            }
        }
        imageUpdate.add("frames",frames);
        imageUpdate.addProperty("start",start);
        imageUpdate.addProperty("stop", stop);
        imageUpdate.addProperty("interval", interval);

        return imageUpdate.toString();
    }

    private Object getResourceGif() throws FileNotFoundException {
        File image = resouceGif;


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");


        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(image.length())
                .contentType(MediaType.IMAGE_GIF)
                .body(new InputStreamResource(new FileInputStream(image)));


    }
    private void writeResourceGif() throws IOException {
        resouceGif = new File("resources.gif");
        ImageOutputStream output = new FileImageOutputStream(resouceGif);
        GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB,1, false);
        int currentFrame = 0;
        for (String key : spriteDict.keySet()) {
            for (Frame f: spriteDict.get(key).getFrames() ) {
                f.setFrameNumber(currentFrame++);
                BufferedImage img = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < f.getHeight(); i++) {
                    for (int j = 0; j < f.getLength(); j++) {
                        img.setRGB(j, i, f.getPixel(i, j).getRGB());
                    }
                }
                writer.writeToSequence(img);


            }
        }
        writer.close();
        output.close();
    }



    @Override
    public String set(String what, String to, Map<String, String> requestParams) {

        return null;
    }

    @Override
    public String getStateJSON() {
        return "";
    }

    @Override
    public void update() {

    }


    public static void main(String[] args)
    {
        ClockDisplaySystem system = new ClockDisplaySystem(null);
        try {
            system.writeResourceGif();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
