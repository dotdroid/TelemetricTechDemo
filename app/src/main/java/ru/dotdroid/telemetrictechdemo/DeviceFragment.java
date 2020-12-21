package ru.dotdroid.telemetrictechdemo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dotdroid.telemetrictechdemo.devices.Device;
import ru.dotdroid.telemetrictechdemo.devices.DeviceLab;

public class DeviceFragment extends Fragment {

    private static final String ARG_DEVICE_EUI = "device_eui";

    private Device mDevice;

    private TextView mTitleTextView, mCreateDateTextView, mTypeTextView,
            mDevEUITextTextView, mDevEUITextView, mAppKeyTextTextView, mAppKeyTextView,
            mDescriptionTextView, mLastMessageTextView;

    public static DeviceFragment newInstance(String deviceEUI) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE_EUI, deviceEUI);

        DeviceFragment fragment = new DeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String deviceEui = (String) getArguments().getSerializable(ARG_DEVICE_EUI);
        mDevice = DeviceLab.get(getActivity()).getDevice(deviceEui);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_device, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_device:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewForFragment = inflater.inflate(R.layout.fragment_device, container, false);

        mTitleTextView = (TextView) viewForFragment.findViewById(R.id.device_title);
        mTitleTextView.setText(mDevice.getTitle());
        mCreateDateTextView = (TextView) viewForFragment.findViewById(R.id.device_create_date);
        mCreateDateTextView.setText(mDevice.getCreatedAt());
        mTypeTextView = (TextView) viewForFragment.findViewById(R.id.device_type);
        mTypeTextView.setText(mDevice.getTypeTitle());
        mDevEUITextTextView = (TextView) viewForFragment.findViewById(R.id.device_deveui_text);
        mDevEUITextTextView.setText(R.string.deveui);
        mDevEUITextView = (TextView) viewForFragment.findViewById(R.id.device_deveui);
        mDevEUITextView.setText(mDevice.getDeviceEui());
        mAppKeyTextTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey_text);
        mAppKeyTextTextView.setText(R.string.app_key);
        mAppKeyTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey);
        mAppKeyTextView.setText(mDevice.getKeyAp());
        mDescriptionTextView = (TextView) viewForFragment.findViewById(R.id.device_desc);
        mDescriptionTextView.setText(mDevice.getDesc());
        mLastMessageTextView = (TextView) viewForFragment.findViewById(R.id.device_last_message);
        mLastMessageTextView.setText(mDevice.getLastMessage());

        return viewForFragment;
    }
}
