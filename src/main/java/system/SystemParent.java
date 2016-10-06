package system;

import controller.Engine;

/**
 * Created by Willi on 9/26/2016.
 */
public abstract class SystemParent {

    Engine e;
    public SystemParent(Engine e)
    {
        this.e = e;
    }

    public  abstract Object get(String what);
    public abstract String set(String what, String to);

    public abstract String getStateJSON();
    public abstract void update();
}
