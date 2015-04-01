package body7.ming.sensordatalog.wear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.LinkedList;
import java.util.List;

import body7.ming.sensordatalog.shared.SensorData;

public class WearableSensorService extends WearableListenerService implements SensorEventListener {

    private final static String TAG = "Wearable Sensor Service";

    private SensorManager mSensorManager;
    private DeviceClient mDeviceClient;

    private List<Sensor> listenedSensorList = new LinkedList<>();

    public WearableSensorService() {
    }

    /***
     * Wearable Data Listener
     */

    @Override
    public void onCreate() {
        super.onCreate();
        mDeviceClient = DeviceClient.getInstance(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);

        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = dataEvent.getDataItem();
                Uri uri = dataItem.getUri();
                String path = uri.getPath();
            }
        }
    }


    //message api send control information and data api sync log data
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "Received message: " + messageEvent.getPath());

        if (messageEvent.getPath().equals("some message")) {

        }
        //add or delete listener
        addSensorListener();
        deleteSensorListener();
        clearSensorListener();
    }


    /***
     *
     * Sensor Event Listener
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        SensorData sensorData = new SensorData(event);
        mDeviceClient.sendSensorData(sensorData);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void addSensorListener(){
        for(Sensor s : listenedSensorList){
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(s.getType()),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void deleteSensorListener(){

    }
    private void clearSensorListener(){

    }
}
