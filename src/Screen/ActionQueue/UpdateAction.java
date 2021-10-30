package Screen.ActionQueue;

import Objects.AllObjectsInterface;
import java.lang.reflect.*;

/**
 * Class for an action that can be taken at a specific time
 */
public class UpdateAction implements Comparable<UpdateAction> {

    private Method aMethod;
    private AllObjectsInterface drawObject;
    private Object[] params;
    private long priority;
    
    /**
     * Constructor
     * @param drawObject Object to perform the action on 
     * @param methodName name of the method to perform
     * @param params Object[] of parameters of the method
     * @param priority time to execute at
     * @throws Exception
     */
    public UpdateAction(AllObjectsInterface drawObject, String methodName, Object[] params, long priority) throws Exception {
        this.drawObject = drawObject;
        this.priority = priority;
        this.params = params;
        this.aMethod = drawObject.getClass().getMethod(methodName, Object[].class);
    }

    /**
     * Runs the specific method
     * @throws Exception
     */
    public void run() throws Exception {
        Object[] param = {params};
        aMethod.invoke(drawObject, param);
    }

    /**
     * @return long priority
     */
    public long getPriority() {
        return priority;
    }

    /**
     * @return Method method
     */
    public Method getMethod() {
        return aMethod;
    }

    /**
     * Compares the priorities of two UpdateActions
     * @return int compare result
     */
    public int compareTo(UpdateAction other) {
        return ((Long)(this.priority - other.getPriority())).intValue();
    }
}
