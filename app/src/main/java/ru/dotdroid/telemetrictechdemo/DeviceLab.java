package ru.dotdroid.telemetrictechdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceLab {
    private static DeviceLab sDeviceLab;
    private List<Device> mDevices;
    private JSONArray allDevices = MainScreenFragment.sAllDevicesJSONArray;

    private static final String TAG = "DeviceLab";

    public static DeviceLab get(Context context) {
        if(sDeviceLab == null) {
            sDeviceLab = new DeviceLab(context);
        }
        return sDeviceLab;
    }

    private DeviceLab(Context context) {
        mDevices = new ArrayList<>();
        new createList().execute();
    }

    public List<Device> getDevices() {
        return mDevices;
    }

    public Device getDevice(String devEui) {
        for(Device device : mDevices) {
            if(device.getDevEUI().equals(devEui)) {
                return device;
            }
        }
        return null;
    }

    private class createList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            for(int i=0; i < allDevices.length(); i++) {
                Device device = new Device();
                try{
                    device.setTitle(allDevices.getJSONObject(i).getString("title"));
                    device.setDevEUI(allDevices.getJSONObject(i).getString("deviceID"));
                    device.setAppKey(allDevices.getJSONObject(i).getString("keyAp"));
                    device.setDeviceType(allDevices.getJSONObject(i).getString("device_type_id"));
                    mDevices.add(device);
                } catch (JSONException jse) {
                    Log.e(TAG, "JSONException: " + jse);
                }
            }
            return null;
        }
    }

}
