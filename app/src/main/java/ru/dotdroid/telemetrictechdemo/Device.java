package ru.dotdroid.telemetrictechdemo;

public class Device {

    private String mTitle;
    private String mDevEUI;
    private String mDeviceType;
    private String mAppKey;

    public Device() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDevEUI() {
        return mDevEUI;
    }

    public void setDevEUI(String devEUI) {
        mDevEUI = devEUI;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
    }

    public String getAppKey() {
        return mAppKey;
    }

    public void setAppKey(String appKey) {
        mAppKey = appKey;
    }
}
