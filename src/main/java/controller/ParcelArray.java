package controller;

import org.json.hue.JSONArray;
import org.json.hue.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Willi on 12/26/2016.
 * Class to minmic a json array object
 * can also store all the things I need in it
 */
public class ParcelArray extends ArrayList<Object>{
    public ParcelArray(){
        super();
    }

    public static ParcelArray PROCESS_JSONARRAY(JSONArray jsonArray){
        ParcelArray pa = new ParcelArray();
        for(int i=0;i<jsonArray.length();i++){
            Object o = jsonArray.get(i);
            if(o instanceof Integer){
                pa.add(jsonArray.getInt(i));
            }
            else if(o instanceof Double){
                pa.add(jsonArray.getDouble(i));
            }
            else if(o instanceof Boolean){
                pa.add(jsonArray.getBoolean(i));
            }
            else if(o instanceof JSONObject){
                Parcel p = Parcel.PROCESS_JSONOBJ((JSONObject) o);
                pa.add(p);
            }
            else if(o instanceof JSONArray){
                pa.add(ParcelArray.PROCESS_JSONARRAY(jsonArray.getJSONArray(i)));
            }
            else if(o instanceof Long){
                pa.add(jsonArray.getLong(i));
            }
            else if(o instanceof String){
                pa.add(jsonArray.getString(i));
            }
        }
        return pa;
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Parcel.class);
        return ret;
    }

    public static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    public String toString(){
        String s = "[";
        for(Object o : this) {

            if (isWrapperType(o.getClass())){
                s+= o.toString() + ", ";
            }
            else {
                s += "\"" + o.toString() + "\", ";
            }

        }
        if(size() > 0) {
            s = s.substring(0, s.length() - 2);
        }
        s+= "]";
        return s;
    }

    public String getString(int index) throws ParcelException{
        if(index >= this.size() || index < 0){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(), ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        return this.get(index).toString();
    }

    public Parcel getParcel(int index) throws ParcelException {
        if(index >= this.size()){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(),ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        Object o = this.get(index);
        if(o instanceof Parcel){
            return (Parcel) o;
        }
        throw new ParcelException("ParcelArray Parse Error, expected: Parcel, found: " + o.getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
    }

    public Double getDouble(int index) throws ParcelException {
        if(index >= this.size()){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(), ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        Object o = this.get(index);
        if(o instanceof Double || o instanceof Integer || o instanceof Long){
            return (Double) this.get(index);
        }
        if(isNumeric(o.toString()))
        {
            return Double.parseDouble(o.toString());
        }
        throw new ParcelException("ParcelArray Parse Error, expected: Double, found: " + o.getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
    }

    public Integer getInteger(int index) throws ParcelException {
        if(index >= this.size()){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(), ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        Object o = this.get(index);
        if(o instanceof Double ||  o instanceof Integer || o instanceof Long){
            return (Integer) this.get(index);
        }
        if(isNumeric(o.toString())){
            return Integer.parseInt(o.toString());
        }
        throw new ParcelException("ParcelArray Parse Error, expected: Integer, found: " + o.getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
    }

    public Long getLong(int index) throws ParcelException {
        if(index >= this.size()){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(), ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        Object o = this.get(index);
        if(o instanceof Double || o instanceof Integer || o instanceof Long){
            return (Long) this.get(index);
        }
        if(isNumeric(o.toString()))
        {
            return Long.parseLong(o.toString());
        }
        throw new ParcelException("ParcelArray Parse Error, expected: Long, found: " + this.get(index).getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
    }

    public Boolean getBoolean(int index) throws ParcelException {
        if(index >= this.size()){
            throw new ParcelException("ParcelArray index out of bounds: index " + index + " size: " + this.size(), ParcelException.PARCEL_ARRAY_INDEX_BOUNDS, this);
        }
        Object o = this.get(index);
        if(o instanceof Boolean){
            return (Boolean) this.get(index);
        }
        switch (o.toString()){
            case "True":
            case "true":
            case "TRUE":
                return true;
            case "FALSE":
            case "False":
            case "false":
                return false;
        }

        throw new ParcelException("ParcelArray Parse Error, expected: Boolean, found: " + this.get(index).getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
    }




    public ArrayList<Parcel> getParcelArray() throws ParcelException{
        ArrayList<Parcel> pList = new ArrayList<Parcel>();
        for(Object o : this){
            if(o instanceof Parcel) {
                pList.add((Parcel) o);
            }
            else{
                throw new ParcelException("ParcelArray Parse Error, expected: Parcel, found: " + o.getClass(), ParcelException.PARCEL_ARRAY_PARSE_ERROR, this);
            }
        }
        return pList;
    }


    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }


}
