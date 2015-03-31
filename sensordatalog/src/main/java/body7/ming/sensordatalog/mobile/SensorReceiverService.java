package body7.ming.sensordatalog.mobile;

import android.hardware.Sensor;

import com.google.android.gms.wearable.WearableListenerService;

import java.util.LinkedList;
import java.util.List;


public class SensorReceiverService extends WearableListenerService{


    private List<Sensor> listenedSensorList = new LinkedList<>();
}
