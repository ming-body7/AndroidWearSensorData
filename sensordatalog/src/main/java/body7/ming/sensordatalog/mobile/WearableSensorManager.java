package body7.ming.sensordatalog.mobile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by MXU24 on 4/9/2015.
 */
public class WearableSensorManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Wearable Sensor manager";

    private static WearableSensorManager instance;

    private Context context;
    private ExecutorService executorService;

    private GoogleApiClient googleApiClient;
    private String nodeId;

    public static synchronized WearableSensorManager getInstance(Context context){
        if(instance == null){
            instance = new WearableSensorManager(context.getApplicationContext());
        }

        return instance;
    }

    private WearableSensorManager(Context context){
        this.context = context;
        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.googleApiClient.connect();
    }

    public void retrieveDeviceNode(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                googleApiClient.blockingConnect(1000, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0) {
                    nodeId = nodes.get(0).getId();
                }
                sendMessageToWearable();
                //googleApiClient.disconnect();
            }
        }).start();
    }
    /***
     * send operation command part
     */

    private void endRemoteSensorService(){

    }
    private void addSensorListener(int sensorType){

    }
    private void deleteSensorListener(int sensorType){

    }
    private void clearSensorListener(){

    }
    //basic function
    private void sendMessageToWearable(){
        googleApiClient.blockingConnect(1000, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(googleApiClient, nodeId, TAG, new byte[0]).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
            @Override
            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                if(!sendMessageResult.getStatus().isSuccess()){
                    //log not successful
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "connect to google api client");
        retrieveDeviceNode();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
