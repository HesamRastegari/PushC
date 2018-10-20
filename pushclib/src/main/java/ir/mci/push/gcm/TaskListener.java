package ir.mci.push.gcm;

/**
 * Created by MSaudi on 2/3/2015.
 */
public interface TaskListener {
    public void onSuccess(String msg);
    public void onFail(String msg);
}
