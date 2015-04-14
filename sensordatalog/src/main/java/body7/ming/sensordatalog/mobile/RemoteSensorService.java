package body7.ming.sensordatalog.mobile;

import android.hardware.Sensor;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.LinkedList;
import java.util.List;

import body7.ming.sensordatalog.shared.SensorData;
import body7.ming.sensordatalog.shared.SensorDataStream;


public class RemoteSensorService extends WearableListenerService{
    private static final String TAG = "SensorReceiverService";


    private List<Sensor> listenedSensorList = new LinkedList<>();
    private GoogleApiClient googleApiClient;
    private WearableSensorManager wearableSensorManager;
    private SensorDataStream sensorDataStream;

    @Override
    public void onCreate(){
        super.onCreate();
        wearableSensorManager = WearableSensorManager.getInstance(this);
        sensorDataStream = new SensorDataStream();
        Log.i(TAG, "start service");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents){

        Log.i(TAG, "receive data");
        wearableSensorManager.retrieveDeviceNode();
        for (DataEvent event : dataEvents) {
            Uri uri = event.getDataItem().getUri();
            Log.i(TAG, "On Data Changed");
            if (event.getType() == DataEvent.TYPE_DELETED) {
                Log.i(TAG, "DataItem deleted: " + event.getDataItem().getUri());
            } else if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.i(TAG, "DataItem changed: " + event.getDataItem().getUri());
                Uri dataItemUri = event.getDataItem().getUri();
                DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem())
                        .getDataMap();
                SensorData data = new SensorData(dataMap);
                //sensorDataStream.addData(data);
            }
        }
    }
    public void addSensorListener(){
        //send a message to add sensor listener and get call back
        //the activity will call this function by IPC?  ot something else
    }
}
