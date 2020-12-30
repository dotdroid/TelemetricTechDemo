package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;

public class DevicePagerActivity extends AppCompatActivity {

    public static final String EXTRA_DEVICE_EUI = "ru.dotdroid.telemetrictechdemo.device_eui";

    private ViewPager mViewPager;
    private List<Device> mDevices;

    public static Intent newIntent(Context packageContext, String deviceEui) {
        Intent intent = new Intent(packageContext, DevicePagerActivity.class);
        intent.putExtra(EXTRA_DEVICE_EUI, deviceEui);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_pager);

        mViewPager = (ViewPager) findViewById(R.id.device_view_pager);

        String deviceEui = (String) getIntent().getSerializableExtra(EXTRA_DEVICE_EUI);
        mDevices = DeviceLab.get(this).getDevices();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Device device = mDevices.get(position);
                Fragment fragment = DeviceFragment.newInstance(device.getDeviceEui());
                return fragment;
            }

            @Override
            public int getCount() {
                return mDevices.size();
            }
        };
        mViewPager.setAdapter(pagerAdapter);
        for(int i=0; i < mDevices.size(); i++) {
            if(mDevices.get(i).getDeviceEui().equals(deviceEui)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }
}
