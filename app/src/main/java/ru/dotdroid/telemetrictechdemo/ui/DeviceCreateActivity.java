package ru.dotdroid.telemetrictechdemo.ui;

import androidx.fragment.app.Fragment;

public class DeviceCreateActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DeviceCreateFragment();
    }
}
