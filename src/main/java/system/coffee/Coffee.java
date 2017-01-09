package system.coffee;

import com.google.gson.JsonObject;
import controller.Engine;
import parcel.Parcel;
import parcel.SystemException;
import system.SystemParent;

/**
 * Created by Willi on 12/4/2016.
 */
public class Coffee extends SystemParent {

    public Coffee(Engine e){
        super(e, 1000);

    }

    @Override
    public Parcel process(Parcel p){
        try {
            switch (p.getString("op")){
                case "makeCoffee":
                    makeCoffee();
                    return  Parcel.RESPONSE_PARCEL("starting to make coffee");
                default:
                    throw SystemException.OP_NOT_SUPPORTED(p);
            }
        } catch (SystemException e) {
            return Parcel.RESPONSE_PARCEL_ERROR(e);
        }

    }

    private void makeCoffee(){
        JsonObject json = new JsonObject();
        json.addProperty("system", "coffee");
        json.addProperty("msg", "makeCoffee");
        engine.sendWSMessage(json.toString());
    }
}

