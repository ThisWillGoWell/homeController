package controller.get;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Willi on 10/24/2016.
 */
public class GetMessage {

    private Map<String,String> allRequestParams;
    public GetMessage(Map<String, String> allRequestParams)
    {
        this.allRequestParams = allRequestParams;
    }

    public GetMessage(){

    }

    public Map<String, String> getAllRequestParams() {
        return allRequestParams;
    }

    public void setAllRequestParams(Map<String, String> allRequestParams) {
        this.allRequestParams = allRequestParams;
    }
}
