package ru.dotdroid.telemetrictechdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewForFragment = inflater.inflate(R.layout.fragment_device, container, false);

        mTitleTextView = (TextView) viewForFragment.findViewById(R.id.device_title);
        mTitleTextView.setText(mDevice.getTitle());
        mCreateDateTextView = (TextView) viewForFragment.findViewById(R.id.device_create_date);
        mCreateDateTextView.setText(mDevice.getCreateDate());
        mTypeTextView = (TextView) viewForFragment.findViewById(R.id.device_type);
        mTypeTextView.setText(mDevice.getDeviceType());
        mDevEUITextTextView = (TextView) viewForFragment.findViewById(R.id.device_deveui_text);
        mDevEUITextTextView.setText("DevEUI: ");
        mDevEUITextView = (TextView) viewForFragment.findViewById(R.id.device_deveui);
        mDevEUITextView.setText(mDevice.getDevEUI());
        mAppKeyTextTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey_text);
        mAppKeyTextTextView.setText("Appkey: ");
        mAppKeyTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey);
        mAppKeyTextView.setText(mDevice.getAppKey());
        mDescriptionTextView = (TextView) viewForFragment.findViewById(R.id.device_desc);
        mDescriptionTextView.setText(mDevice.getDescription());
        mLastMessageTextView = (TextView) viewForFragment.findViewById(R.id.device_last_message);
        mLastMessageTextView.setText(mDevice.getLastMessage());

        return viewForFragment;
    }
}
