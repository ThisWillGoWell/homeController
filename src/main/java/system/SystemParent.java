package system;

/**
 * Created by Willi on 9/26/2016.
 */
public abstract class SystemParent {

    public SystemParent()
    {

    }

    public abstract String getStateJSON();
    public abstract void update();
}
