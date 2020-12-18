package ru.dotdroid.telemetrictechdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

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
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Device device = mDevices.get(position);
                return DeviceFragment.newInstance(device.getDevEUI());
            }

            @Override
            public int getCount() {
                return mDevices.size();
            }
        });
        for(int i=0; i < mDevices.size(); i++) {
            if(mDevices.get(i).getDevEUI().equals(deviceEui)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }
}
