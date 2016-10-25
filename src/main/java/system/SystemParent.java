package system;

import controller.Engine;

import java.util.Map;

/**
 * Created by Willi on 9/26/2016.
 */
public abstract class SystemParent {

    protected Engine e;
    public SystemParent(Engine e)
    {
        this.e = e;
    }

    public  abstract Object get(String what, Map<String, String> requestParams);
    public abstract String set(String what, String to, Map<String, String> requestParams);

    public abstract String getStateJSON();
    public abstract void update();
}
