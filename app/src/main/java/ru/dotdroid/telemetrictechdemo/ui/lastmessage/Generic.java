package ru.dotdroid.telemetrictechdemo.ui.lastmessage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.json.DeviceMessage;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;

import static ru.dotdroid.telemetrictechdemo.utils.MyDateFormat.*;

public class Generic extends Fragment {

    private static final String ARG_DEVICE_EUI = "device_eui";

    private TextView mReceiveTimestampTextView, mReceiveTimestampTextViewValue,
            mRecordTimestampTextView, mRecordTimestampTextViewValue, mRssiTextView,
            mRssiTextViewValue, mBatteryTextView, mBatteryTextViewValue;

    private Device mDevice;

    public static Generic newInstance(String deviceEui) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE_EUI, deviceEui);
        Generic fragment = new Generic();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String deviceEui = (String) getParentFragment().getArguments().getSerializable(ARG_DEVICE_EUI);
        mDevice = DeviceLab.get(getActivity()).getDevice(deviceEui);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DeviceMessage.Messages lastMessage = new Gson().fromJson(mDevice.getLastMessage(), DeviceMessage.Messages.class);

        View view = inflater.inflate(R.layout.fragment_message_generic, container, false);
        mReceiveTimestampTextView = (TextView) view.findViewById(R.id.message_list_receive_timestamp);
        mReceiveTimestampTextView.setText(R.string.receive_timestamp);
        mReceiveTimestampTextViewValue = (TextView) view.findViewById(R.id.message_list_receive_timestamp_value);
        mRecordTimestampTextView = (TextView) view.findViewById(R.id.message_list_record_timestamp);
        mRecordTimestampTextView.setText(R.string.record_timestamp);
        mRecordTimestampTextViewValue = (TextView) view.findViewById(R.id.message_list_record_value);
        mRssiTextView = (TextView) view.findViewById(R.id.messages_list_rssi);
        mRssiTextView.setText(R.string.rssi);
        mRssiTextViewValue = (TextView) view.findViewById(R.id.messages_list_rssi_value);
        mBatteryTextView = (TextView) view.findViewById(R.id.message_list_battery);
        mBatteryTextView.setText(R.string.battery);
        mBatteryTextViewValue = (TextView) view.findViewById(R.id.message_list_battery_value);

        if (mDevice.getLastMessage() != null) {
            mReceiveTimestampTextViewValue.setText(unixToDate(lastMessage.getDateTime()));
            if (lastMessage.getRecordTimestamp() != 0) {
                mRecordTimestampTextViewValue.setText(unixToDate(lastMessage.getRecordTimestamp()));
            } else {
                mRecordTimestampTextViewValue.setText(unixToDate(lastMessage.getRecordTimestamp2()));
            }
            mRssiTextViewValue.setText(String.valueOf(lastMessage.getLoRaRssi()));
            if(lastMessage.getBatteryLevel() != 0) {
                mBatteryTextViewValue.setText(String.valueOf(lastMessage.getBatteryLevel()));
            } else {
                mBatteryTextViewValue.setText(String.valueOf(lastMessage.getBatteryLevel2()));
            }
        } else {
            mReceiveTimestampTextViewValue.setText(R.string.no_data);
            mRecordTimestampTextViewValue.setText(R.string.no_data);
            mRssiTextViewValue.setText(R.string.no_data);
            mBatteryTextViewValue.setText(R.string.no_data);
        }
        return view;
    }
}

