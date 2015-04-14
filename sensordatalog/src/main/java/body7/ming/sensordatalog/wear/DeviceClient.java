package body7.ming.sensordatalog.wear;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import body7.ming.sensordatalog.shared.SensorData;

/***
 * manage the connection and data transfer
 */
public class DeviceClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Wearable Device Client";

    public static DeviceClient instance;

    public static DeviceClient getInstance(Context context){
        if(instance == null){
            instance = new DeviceClient(context.getApplicationContext());
        }
        Log.i(TAG,"get device client");
        return instance;
    }

    private Context context;
    private GoogleApiClient googleApiClient;
    private ExecutorService executorService;

    private DeviceClient(Context context){
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        executorService = Executors.newCachedThreadPool();
        googleApiClient.connect();
    }

    public void sendSensorData(final SensorData data){
        Log.i(TAG,"send sensor data");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                sendSensorDataInBackground(data);
            }
        });
    }

    private void sendSensorDataInBackground(SensorData data){
        String path = "/SensorData/"+ System.currentTimeMillis();
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
        data.fillDataMap(putDataMapRequest.getDataMap());
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        Log.i(TAG, "Sending sensor data successful: " + dataItemResult.getStatus()
                                .isSuccess());
                    }
                });
        Log.i(TAG, "send sensor data");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "google api connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "google api connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "google api connect failed");
        Log.i(TAG, result.toString());
    }
}
