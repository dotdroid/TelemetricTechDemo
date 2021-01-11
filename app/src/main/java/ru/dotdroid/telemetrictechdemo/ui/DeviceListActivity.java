package ru.dotdroid.telemetrictechdemo.ui;

import androidx.fragment.app.Fragment;

public class DeviceListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DeviceListFragment();
    }
}
