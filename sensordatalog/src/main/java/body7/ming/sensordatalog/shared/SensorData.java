package body7.ming.sensordatalog.shared;

import android.hardware.SensorEvent;

import com.google.android.gms.wearable.DataMap;

public class SensorData {
    private int type;
    private int accuracy;
    private long timestamp;
    private float[] values;
    //private DataMap dataMap;
    public SensorData(SensorEvent event){
        this.type = event.sensor.getType();
        this.accuracy = event.accuracy;
        this.timestamp = event.timestamp;
        this.values = event.values;
        //this.dataMap = new DataMap();
        //initDataMap();
    }

    public void fillDataMap(DataMap dataMap){
        dataMap.putInt("type",this.type);
        dataMap.putInt("accuracy", this.accuracy);
        dataMap.putLong("timestamp", this.timestamp);
        dataMap.putFloatArray("values", this.values);
        //this.dataMap = dataMap;
    }

    public SensorData(DataMap dataMap){
        this.type = dataMap.getInt("type");
        this.accuracy = dataMap.getInt("accuracy");
        this.timestamp = dataMap.getLong("timestamp");
        this.values = dataMap.getFloatArray("values");
        //this.dataMap = dataMap;
    }

    //public DataMap getSensorDataMap(){
        //return this.dataMap;
    //}
}
