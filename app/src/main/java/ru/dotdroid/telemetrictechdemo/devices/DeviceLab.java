package ru.dotdroid.telemetrictechdemo.devices;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DeviceLab {
    private static DeviceLab sDeviceLab;
    private final List<Device> mDevices;
    private final List<DeviceType.types> mDeviceTypes;
    private final List<DeviceMessage.mMessages> mDeviceMessages;

    private static final String TAG = "DeviceLab";

    public static DeviceLab get(Context context) {
        if(sDeviceLab == null) {
            sDeviceLab = new DeviceLab(context);
        }
        return sDeviceLab;
    }

    private DeviceLab(Context context) {
        mDevices = new ArrayList<>();
        mDeviceTypes = new ArrayList<>();
        mDeviceMessages = new ArrayList<>();
    }

    public List<Device> getDevices() {
        return mDevices;
    }

    public void clearLists() {
        mDevices.clear();
        mDeviceTypes.clear();
        mDeviceMessages.clear();
    }

    public List<DeviceType.types> getDeviceTypes() {
        return mDeviceTypes;
    }

    public Device getDevice(String deviceEui) {
        for(Device device : mDevices) {
            if(device.getDeviceEui().equals(deviceEui)) {
                return device;
            }
        }
        return null;
    }

}
