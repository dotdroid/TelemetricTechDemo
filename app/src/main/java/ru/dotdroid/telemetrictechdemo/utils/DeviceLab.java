package ru.dotdroid.telemetrictechdemo.utils;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.json.DeviceMessage;
import ru.dotdroid.telemetrictechdemo.json.DeviceType;

public class DeviceLab {
    private static DeviceLab sDeviceLab;
    private final List<Device> mDevices;
    private final List<DeviceType.Types> mDeviceTypes;
    private final List<DeviceMessage.Messages> mDeviceMessages;

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

    public List<DeviceType.Types> getDeviceTypes() {
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
