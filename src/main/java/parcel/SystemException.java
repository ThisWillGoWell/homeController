package parcel;

import system.SystemParent;

public class SystemException extends Exception{
    static int EXCEPTION = 0;
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
    static int ACCESS_DENIED = 11;
    static int UPDATE_FAILED = 12;
    private int error;

    Object source;
    public SystemException(String msg, int error, Object p){
        super(msg);
        this.error = error;
        source = p;
    }

    public static SystemException SYSTEM_NOT_FOUND_EXCEPTION(NullPointerException e, Parcel p){
        return new SystemException(e.getMessage(), SYSTEM_NOT_FOUND, p);
    }

    public static SystemException OP_NOT_SUPPORTED(Parcel p){
        return new SystemException("op not supported", OP_NOT_SUPPORTED, p);
    }

    public static SystemException WHAT_NOT_SUPPORTED(Parcel p){
        return new SystemException("what not supported", WHAT_NOT_SUPPORTED,p);
    }

    public static SystemException TO_NOT_SUPPORTED(Parcel p){
        return new SystemException("to not supported", TO_NOT_SUPPORTED,p);
    }
    int getError(){
        return error;
    }

    public static SystemException ACCESS_DENIED(Parcel p){
        return new SystemException("Access Denied: op not suppored on what",ACCESS_DENIED, p );
    }
    public static SystemException GENERIC_EXCEPTION(Object source, Exception e){
        return new SystemException(e.toString(), EXCEPTION, source);
    }


}
