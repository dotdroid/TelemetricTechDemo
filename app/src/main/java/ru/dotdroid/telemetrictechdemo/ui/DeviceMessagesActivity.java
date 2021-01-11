package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.ui.messages.Generic;
import ru.dotdroid.telemetrictechdemo.ui.messages.Inertia;
import ru.dotdroid.telemetrictechdemo.ui.messages.Water;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;

public class DeviceMessagesActivity extends AppCompatActivity {

    private static final String TAG = "DeviceMessagesActivity";

    public static final String EXTRA_DEVICE_EUI = "ru.dotdroid.telemetrictechdemo.device_eui";
    public static final String EXTRA_MESSAGES_START = "ru.dotdroid.telemetrictechdemo.messages_start";
    public static final String EXTRA_MESSAGES_END = "ru.dotdroid.telemetrictechdemo.messages_end";

    private String mDeviceId, mDeviceEui, mDeviceTypeId;
    private long mMessagesStart, mMessagesEnd;

    private Device mDevice;

    public static Intent newIntent(Context packageContext, String deviceEui, long messagesStart, long messagesEnd) {
        Intent intent = new Intent(packageContext, DeviceMessagesActivity.class);
        intent.putExtra(EXTRA_DEVICE_EUI, deviceEui);
        intent.putExtra(EXTRA_MESSAGES_START, messagesStart);
        intent.putExtra(EXTRA_MESSAGES_END, messagesEnd);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mDeviceEui = (String) getIntent().getSerializableExtra(EXTRA_DEVICE_EUI);
        mMessagesStart = (Long) getIntent().getSerializableExtra(EXTRA_MESSAGES_START);
        mMessagesEnd = (Long) getIntent().getSerializableExtra(EXTRA_MESSAGES_END);
        mDevice = DeviceLab.get(DeviceMessagesActivity.this).getDevice(mDeviceEui);
        mDeviceId = mDevice.getId();
        mDeviceTypeId = mDevice.getDeviceTypeId();

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (mDevice.getDeviceTypeId().equals("66")) {
            fragment = Water.newInstance(mDeviceId, mMessagesStart, mMessagesEnd);
        } else if (mDevice.getDeviceTypeId().equals("82")) {
            fragment = Inertia.newInstance(mDeviceId, mMessagesStart, mMessagesEnd);
        } else {
            fragment = Generic.newInstance(mDeviceId, mMessagesStart, mMessagesEnd);
        }
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }
}
