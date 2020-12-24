package ru.dotdroid.telemetrictechdemo;

import android.support.v4.app.Fragment;

public class DeviceMessagesActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new DeviceMessagesFragment();
    }
}
