package controller;

public class ParcelException extends Exception{
    static int KEY_NOT_FOUND = 1;
    static int CLASS_CAST_ERROR = 2;
    static int INVLAID_JSON_STR = 3;
    static int SYSTEM_NOT_FOUND = 4;
    static int OP_NOT_SUPPORTED = 5;
    static int PAYLOAD_NOT_FOUND = 6;
    static int WHAT_NOT_SUPPORTED = 7;
    static int TO_NOT_SUPPORTED = 8;
    static int PARCEL_ARRAY_PARSE_ERROR = 9;
    static int PARCEL_ARRAY_INDEX_BOUNDS = 10;
    private int error;

    Object source;
    public ParcelException(String msg, int error, Parcel p){
        super(msg);
        this.error = error;
        source = p;
    }


    public ParcelException(String msg, int error, ParcelArray p){
        super(msg);
        this.error = error;
        source = p;
    }


    public static ParcelException SYSTEM_NOT_FOUND_EXCEPTION(NullPointerException e, Parcel p){
        return new ParcelException(e.getMessage(), SYSTEM_NOT_FOUND, p);
    }

    public static ParcelException OP_NOT_SUPPORTED(Parcel p){
        return new ParcelException("op not supported", OP_NOT_SUPPORTED, p);
    }

    public static ParcelException WHAT_NOT_SUPPORTED(Parcel p){
        return new ParcelException("what not supported", WHAT_NOT_SUPPORTED,p);
    }

    public static ParcelException TO_NOT_SUPPORTED(Parcel p){
        return new ParcelException("to not supported", TO_NOT_SUPPORTED,p);
    }
    int getError(){
        return error;
    }


}
