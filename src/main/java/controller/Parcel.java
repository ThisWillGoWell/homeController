package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.hue.JSONArray;
import org.json.hue.JSONObject;
import org.springframework.web.socket.WebSocketSession;
import system.SystemParent;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

/**
 * Created by Willi on 12/19/2016.
 *
 */
public class Parcel extends HashMap<String, Object>{



    public Parcel() {
        super();
    }


    public static Parcel PROCESS_JSONOBJ(JSONObject jsonObject){
        Parcel p = new Parcel();
        Object resp = null;
        String reply;
        for(Object key : jsonObject.keySet()){
            String k = (String) key;
            if(jsonObject.get(k) instanceof JSONArray){
                p.put(k,ParcelArray.PROCESS_JSONARRAY( jsonObject.getJSONArray(k)));
            }
            else if(jsonObject.get(k) instanceof JSONObject){
                p.put(k, PROCESS_JSONOBJ(jsonObject.getJSONObject(k)));
            }
            else{
                p.put(k, jsonObject.get(k));
            }
        }
        return p;
    }

    public static Parcel PROCESS_JSONSTR(String jsonStr){
        JSONObject m = new JSONObject(jsonStr);
        return PROCESS_JSONOBJ(m);
    }




    public static Parcel GET_PARCEL(String system, String what) {
        Parcel p = new Parcel();
        p.put("op", "get");
        p.put("system", system);
        p.put("what", what);
        return p;
    }


    public static Parcel SET_PARCEL(String system, String what, Object to) {
        Parcel p = new Parcel();
        p.put("op", "get");
        p.put("system", system);
        p.put("what", what);
        p.put("to", to);
        return p;
    }

    public static Parcel RESPONSE_PARCEL_ERROR(ParcelException e){
        Parcel p = new Parcel();
        p.put("success", false);
        p.put("onlyPayload", false);
        p.put("status", e.getError());
        p.put("error", e.toString());
        p.put("payload", "none");
        return p;
    }

    public static Parcel RESPONSE_PARCEL_ERROR(int errorCode, String error ){
        Parcel p = new Parcel();
        p.put("success", false);
        p.put("status", errorCode);
        p.put("onlyPayload", false);
        p.put("error", error);
        p.put("payload", "none");
        return p;
    }

    public static Parcel RESPONSE_PARCEL(Object payload){
        return RESPONSE_PARCEL(payload, false);
    }

    public static Parcel RESPONSE_PARCEL(Object payload, boolean onlyPayload){
        Parcel p = new Parcel();
        p.put("success", true);
        p.put("status", 200);
        p.put("onlyPayload", onlyPayload);
        p.put("error", "None");
        p.put("payload", payload);
        return p;
    }

    public Object toPayload() throws ParcelException {
        if(containsKey("payload")){
            if(containsKey("onlyPayload")){
                if(getBoolean("onlyPayload")){
                    return this.get("payload");
                }
                else{
                    return this.toString();
                }
            }
            else{
                return this.toString();
            }
        }
        throw  new ParcelException("No Payload Found in package", ParcelException.PAYLOAD_NOT_FOUND, this);
    }



    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    public boolean contains(String value) {
        return this.containsKey(value);
    }

    public boolean success(){
        if(containsKey("success")){
            try {
                return this.getBoolean("success");
            } catch (ParcelException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public String getString(String value) throws ParcelException {

        if (this.containsKey(value)) {
            return this.get(value).toString();
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);

    }

    public Long getLong(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if(o instanceof Long)
                return (Long) o;
            if(o instanceof Double)
                return ((Double) o).longValue();
            try {
                return Long.parseLong(o.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Long", ParcelException.CLASS_CAST_ERROR, this);
            }
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }


    public Integer getInteger(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if(o instanceof Double)
                return ((Double) o).intValue();
            if(isNumeric(o.toString())){
                try{
                    return Integer.parseInt(o.toString());
                }
                catch (java.lang.NumberFormatException e){
                    throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Integer", ParcelException.CLASS_CAST_ERROR, this);
                }
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Integer", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }

    public Double getDouble(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if(o instanceof Double){
                return (Double) o;
            }
            if(isNumeric(o.toString())){
                try {
                    return Double.parseDouble(o.toString());
                }
                catch (java.lang.NumberFormatException e){
                    throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Double", ParcelException.CLASS_CAST_ERROR, this);
                }
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Double", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }


    public Parcel getParcel(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if (o instanceof Parcel) {
                return (Parcel) this.get(value);
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Parcel", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }

    public boolean getBoolean(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if (o instanceof Boolean) {
                return (Boolean) this.get(value);
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Boolean", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }

    public WebSocketSession getWebsocketSession(String value) throws ParcelException {
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if (o instanceof WebSocketSession) {
                return (WebSocketSession) this.get(value);
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected Websocket", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }

    public ParcelArray getParcelArray(String value) throws ParcelException{
        if (this.containsKey(value)) {
            Object o = this.get(value);
            if (o instanceof ParcelArray) {
                return (ParcelArray) this.get(value);
            }
            throw new ParcelException("Key " + value + " returns object " + o.toString() + " of class " + o.getClass().toString() + " Expected ParcelArray", ParcelException.CLASS_CAST_ERROR, this);
        }
        throw new ParcelException("Key " + value + " not found in package " + toString(), ParcelException.KEY_NOT_FOUND, this);
    }



    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }


    public static void main(String args[]){
        double d = 1/3;
        int i = 100;
        long l = 50000;
        Parcel p = new Parcel();
        p.put("boolean", true);
        p.put("boolean", false);
        p.put("string", "testString");
        p.put("boolean", true);
        p.put("double", d);
        p.put("int", i);
        p.put("long", l);

        ParcelArray pa = new ParcelArray();
        pa.add(p);
        pa.add(p);
        pa.add(p);

        Parcel top = new Parcel();
        System.out.println(p.toString());

        try {
            System.out.println(p.getInteger("double"));
        } catch (ParcelException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(p.getInteger("int"));
        } catch (ParcelException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(p.getInteger("long"));
        } catch (ParcelException e) {
            e.printStackTrace();
        }


        top.put("array", pa);
        System.out.println(top);
        System.out.println(Parcel.PROCESS_JSONSTR(top.toString()));

    }
}

