package modules;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * Created by Willi on 9/26/2016.
 */
public class WiFiMonitor {

    private String LOGIN_URL = "http://router.asus.com/login.cgi";
    private String LOGIN_CONTENT =   "group_id=&action_mode=&action_script=&" +
                                    "action_wait=5&current_page=Main_Login.asp&"+
                                    "next_page=index.asp&login_authorization=YWRtaW46bWFuYWdlODk%3D";
    private String WIFI_LIST_URL = "http://router.asus.com/update_networkmapd.asp";


    private HttpURLConnection connection;

    private DataOutputStream printout;
    private DataInputStream input;

    private CloseableHttpClient client;

    public WiFiMonitor()
    {
     client = HttpClientBuilder.create().build();
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

    ArrayList<String[]> getWifiList()
    {
        return processResponse("ï»¿fromNetworkmapd = '<0>Philips-hue>192.168.1.140>00:17:88:23:A2:86>1>0>0><0>raspberrypi>192.168.1.153>B8:27:EB:C2:F5:53>0>0>0><1>RaveCave>192.168.1.190>D8:50:E6:4A:63:0B>0>0>0><1>CPPC>192.168.1.188>08:62:66:7F:8E:FE>0>0>0><0>Chromecast>192.168.1.19>A4:77:33:F1:94:32>0>0>0><0>Chromecast>192.168.1.15>A4:77:33:F1:93:76>0>0>0><0>Chromecast>192.168.1.8>A4:77:33:F1:79:D4>0>0>0><0>kindle-87a859199>192.168.1.186>44:65:0D:DD:48:B6>0>0>0><0>Chromecast>192.168.1.62>A4:77:33:03:19:D0>0>0>0><1>DESKTOP-7MI2CCG>192.168.1.84>B4:AE:2B:CB:92:F6>0>0>0><0>Ryans-MBP>192.168.1.104>14:10:9F:CF:41:35>0>0>0><0>will-desktop>192.168.1.125>D0:50:99:54:00:78>0>0>0><0>Chromecast>192.168.1.141>A4:77:33:F1:7A:14>0>0>0><0>android-6bf16baa95a9d59f>192.168.1.242>00:AE:FA:7E:A8:EF>0>0>0><0>iPhone>192.168.1.97>5C:8D:4E:90:E2:75>0>0>0><0>Williams-iPhone>192.168.1.237>E0:AC:CB:DA:3B:67>0>0>0><0>android-7d1b71341c60aa6b>192.168.1.155>AC:22:0B:A8:4E:9D>0>0>0><1>Chris-LAPTOP>192.168.1.246>C4:85:08:05:F3:75>0>0>0><0>mike>192.168.1.212>D0:7E:35:C7:40:A7>0>0>0><0>mikes-iPhone>192.168.1.94>70:EC:E4:70:C7:D0>0>0>0><0>will-desktop>192.168.1.154>D0:50:99:54:00:78>0>0>0><0> >192.168.1.189>CC:95:D7:41:87:B0>0>0>0><0> >192.168.1.231>AC:89:95:29:12:C9>0>0>0><0>android-e98f51a9286f5d46>192.168.1.105>B4:3A:28:F5:24:62>0>0>0><0>Michaels-iPhone>192.168.1.247>24:A0:74:16:66:DB>0>0>0><8>XboxOne>192.168.1.110>30:59:B7:E0:9A:B7>0>0>0><1>DESKTOP-H44G3HI>192.168.1.216>48:E2:44:00:46:A5>0>0>0><1>Papasergi-ASUS>192.168.1.238>DC:53:60:EA:25:45>0>0>0><0>kindle-f6e5458ff>192.168.1.147>F0:27:2D:B6:28:11>0>0>0>'.replace(/&#62/g, \">\").replace(/&#60/g, \"<\").split('<');");
        /*
        try{
            HttpGet request = new HttpGet(WIFI_LIST_URL + "?_=" + System.currentTimeMillis());
            request.setHeader("Content-Type","application/x-www-form-urlencoded");
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            String response = client.execute(request, responseHandler);
            System.out.println(response);
            return processResponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
    }

    ArrayList<String[]> processResponse(String response)
    {
        ArrayList processed = new ArrayList<String[]>();
        String[] responseSplit = response.split("<");
        for(int i=1;i<responseSplit.length-3;i++){
            String[] splitedResponseSplit = responseSplit[i].split(">");

            processed.add(new String[]{splitedResponseSplit[1], splitedResponseSplit[2], splitedResponseSplit[3]});
        }

        for(int j=0;j<processed.size();j++) {
            System.out.println(((String[]) processed.get(j))[0] +((String[]) processed.get(j))[1] + ((String[]) processed.get(j))[2]);
        }
        return processed;

    }

    public static void main(String args[])
    {
        WiFiMonitor wirelessMonitor = new WiFiMonitor();
        //wirelessMonitor.routerLogin();
        wirelessMonitor.getWifiList();
    }
}
