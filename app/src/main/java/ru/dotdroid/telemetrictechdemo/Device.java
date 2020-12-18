package ru.dotdroid.telemetrictechdemo;

public class Device {

    private String mTitle;
    private String mDeviceType;
    private String mDevEUI;
    private String mAppKey;
    private String mDescription;
    private String mCreateDate;
    private String mLastMessage;
    private String mLastActive;

    public Device() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        mDeviceType = deviceType;
    }

    public String getDevEUI() {
        return mDevEUI;
    }

    public void setDevEUI(String devEUI) {
        mDevEUI = devEUI;
    }

    public String getAppKey() {
        return mAppKey;
    }

    public void setAppKey(String appKey) {
        mAppKey = appKey;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String createDate) {
        mCreateDate = createDate;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getLastActive() {
        return mLastActive;
    }

    public void setLastActive(String lastActive) {
        mLastActive = lastActive;
    }
}
