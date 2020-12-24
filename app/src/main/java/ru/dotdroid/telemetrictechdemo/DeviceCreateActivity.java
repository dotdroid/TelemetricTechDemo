package ru.dotdroid.telemetrictechdemo;

import android.support.v4.app.Fragment;

public class DeviceCreateActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new DeviceCreateFragment();
    }
}
