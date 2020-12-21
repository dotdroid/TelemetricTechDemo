package ru.dotdroid.telemetrictechdemo;

import android.support.v4.app.Fragment;

public class CreateDeviceActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CreateDeviceFragment();
    }
}
