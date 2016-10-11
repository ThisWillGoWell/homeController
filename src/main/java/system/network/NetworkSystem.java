package system.network;

import controller.Engine;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import system.SystemParent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Willi on 9/26/2016.
 */
public class NetworkSystem extends SystemParent{

    private final String LOGIN_URL = "http://router.asus.com/login.cgi";
    private final String LOGIN_CONTENT =    "group_id=&action_mode=&action_script=&" +
                                            "action_wait=5&current_page=Main_Login.asp&"+
                                            "next_page=index.asp&login_authorization=YWRtaW46bWFuYWdlODk%3D";
    private final String WIFI_LIST_URL = "http://router.asus.com/update_networkmapd.asp";


    private CloseableHttpClient client;
    private NetworkState state;

    public NetworkSystem(Engine e)
    {
        super(e);
        client = HttpClientBuilder.create().build();
        state = new NetworkState();
        routerLogin();
        update();
    }

    void routerLogin()
    {
        try{
            HttpPost request = new HttpPost(LOGIN_URL + "?" + LOGIN_CONTENT);
            request.setHeader("Content-Type","application/x-www-form-urlencoded");
            HttpResponse response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ArrayList<String[]> getNetworkList()
    {
        try{
            HttpGet request = new HttpGet(WIFI_LIST_URL + "?_=" + System.currentTimeMillis());
            request.setHeader("Content-Type","application/x-www-form-urlencoded");
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            String response = client.execute(request, responseHandler);
            return processResponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    ArrayList<String[]> processResponse(String response)
    {
        ArrayList processed = new ArrayList<String[]>();
        String[] responseSplit = response.split("<");
        for(int i=1;i<responseSplit.length-3;i++){
            String[] splittedResponseSplit = responseSplit[i].split(">");
            //Name, IP, MAC

            processed.add(new String[]{splittedResponseSplit[1], splittedResponseSplit[2], splittedResponseSplit[3]});
        }
        return processed;
    }

    public void update()
    {
        getNetworkList();
    }

    @Override
    public Object get(String what, Map<String,String> requestParams) {
        return null;
    }

    @Override
    public String set(String what, String to, Map<String,String> requestParams) {
        return null;
    }

    public String getStateJSON()
    {
        String s = "\"Network\": {";
            s+= state.getJson();
        s+="}";
        return s;
    }

    public static void main(String args[])
    {
        NetworkSystem wirelessMonitor = new NetworkSystem(new Engine());
        wirelessMonitor.routerLogin();
    }


}
