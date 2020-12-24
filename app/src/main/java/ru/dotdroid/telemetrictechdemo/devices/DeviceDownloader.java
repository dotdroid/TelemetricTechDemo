package ru.dotdroid.telemetrictechdemo.devices;

import android.os.HandlerThread;

public class DeviceDownloader<T> extends HandlerThread {
    private static final String TAG = "DeviceDownloader";
    private static final int DEVICE_LIST_DOWNLOAD = 0;

    private boolean mHasQuit = false;

    public DeviceDownloader() {
        super(TAG);
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }
}
