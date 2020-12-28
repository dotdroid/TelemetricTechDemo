package ru.dotdroid.telemetrictechdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.json.DeviceMessage;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;

import static ru.dotdroid.telemetrictechdemo.utils.MyDateFormat.*;

public class DeviceMessageLastFragment extends Fragment {

    private static final String ARG_DEVICE_EUI = "device_eui";

    private TextView mReceiveTimestampTextView, mReceiveTimestampTextViewValue,
            mRecordTimestampTextView, mRecordTimestampTextViewValue, mRssiTextView,
            mRssiTextViewValue, mBatteryTextView, mBatteryTextViewValue, mReasonTextView,
            mReasonTextViewValue, mDirectFlowTextView, mDirectFlowTextViewValue;

    private TextView mAccelerometerAngleTextView, mAccelerometerAngleValueTextView,
            mMagnetometerAngleTextView, mMagnetometerAngleValueTextView;

    private Device mDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String deviceEui = (String) getParentFragment().getArguments().getSerializable(ARG_DEVICE_EUI);
        mDevice = DeviceLab.get(getActivity()).getDevice(deviceEui);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DeviceMessage.Messages lastMessage = new Gson().fromJson(mDevice.getLastMessage(), DeviceMessage.Messages.class);

        if (mDevice.getDeviceTypeId().equals("82")) {
            View view = inflater.inflate(R.layout.fragment_message_inertia, container, false);

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
            mReasonTextView = (TextView) view.findViewById(R.id.message_list_reason_to_send);
            mReasonTextView.setText(R.string.reason_to_send);
            mReasonTextViewValue = (TextView) view.findViewById(R.id.message_list_reason_to_send_value);
            mAccelerometerAngleTextView = (TextView) view.findViewById(R.id.message_list_acc_angle);
            mAccelerometerAngleTextView.setText(R.string.accelerometer_angle);
            mAccelerometerAngleValueTextView = (TextView) view.findViewById(R.id.message_list_acc_angle_value);
            mMagnetometerAngleTextView = (TextView) view.findViewById(R.id.message_list_mag_angle);
            mMagnetometerAngleTextView.setText(R.string.magnetometer_angle);
            mMagnetometerAngleValueTextView = (TextView) view.findViewById(R.id.message_list_mag_angle_value);

            if (mDevice.getLastMessage() != null) {
                mReceiveTimestampTextViewValue.setText(unixToDate(lastMessage.getDateTime()));
                mRecordTimestampTextViewValue.setText(unixToDate(lastMessage.getRecordTimestamp2()));
                mRssiTextViewValue.setText(String.valueOf(lastMessage.getLoRaRssi()));
                mBatteryTextViewValue.setText(String.valueOf(lastMessage.getBatteryLevel()));
                if (lastMessage.getSignLinkButton() == 1) {
                    mReasonTextViewValue.setText(R.string.link_button);
                } else if (lastMessage.getImpactMagnetFlag() == 1) {
                    mReasonTextViewValue.setText(R.string.magnet);
                } else if (lastMessage.getSignAlarm() == 1) {
                    mReasonTextViewValue.setText(R.string.alarm);
                } else {
                    mReasonTextViewValue.setText(R.string.scheduled);
                }
                mAccelerometerAngleValueTextView.setText(Float.toString(lastMessage.getAccelerometerAngle()));
                mMagnetometerAngleValueTextView.setText(Float.toString(lastMessage.getMagnetometerAngle()));
            } else {
                mReceiveTimestampTextViewValue.setText(R.string.no_data);
                mRecordTimestampTextViewValue.setText(R.string.no_data);
                mRssiTextViewValue.setText(R.string.no_data);
                mBatteryTextViewValue.setText(R.string.no_data);
                mReasonTextViewValue.setText(R.string.no_data);
                mAccelerometerAngleValueTextView.setText(R.string.no_data);
                mMagnetometerAngleValueTextView.setText(R.string.no_data);
            }
            return view;
        } else if (mDevice.getDeviceTypeId().equals("66")) {

            View view = inflater.inflate(R.layout.fragment_message_water, container, false);

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
            mReasonTextView = (TextView) view.findViewById(R.id.message_list_reason_to_send);
            mReasonTextView.setText(R.string.reason_to_send);
            mReasonTextViewValue = (TextView) view.findViewById(R.id.message_list_reason_to_send_value);
            mDirectFlowTextView = (TextView) view.findViewById(R.id.messages_list_directflow);
            mDirectFlowTextView.setText(R.string.direct_flow);
            mDirectFlowTextViewValue = (TextView) view.findViewById(R.id.messages_list_directflow_value);

            if (mDevice.getLastMessage() != null) {
                mReceiveTimestampTextViewValue.setText(unixToDate(lastMessage.getDateTime()));
                mRecordTimestampTextViewValue.setText(unixToDate(lastMessage.getRecordTimestamp()));
                mRssiTextViewValue.setText(String.valueOf(lastMessage.getLoRaRssi()));
                mBatteryTextViewValue.setText(String.valueOf(lastMessage.getBatteryLevel()));
                if (lastMessage.getLeakDetectionFlag() == 1) {
                    mReasonTextViewValue.setText(R.string.leakage);
                } else if (lastMessage.getImpactMagnetFlag() == 1) {
                    mReasonTextViewValue.setText(R.string.magnet);
                } else {
                    mReasonTextViewValue.setText(R.string.scheduled);
                }
                mDirectFlowTextViewValue.setText(String.valueOf(lastMessage.getDirectFlowLiterCounter()));
            } else {
                mReceiveTimestampTextViewValue.setText(R.string.no_data);
                mRecordTimestampTextViewValue.setText(R.string.no_data);
                mRssiTextViewValue.setText(R.string.no_data);
                mBatteryTextViewValue.setText(R.string.no_data);
                mReasonTextViewValue.setText(R.string.no_data);
                mDirectFlowTextViewValue.setText(R.string.no_data);
            }
            return view;
        } else {
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
                mBatteryTextViewValue.setText(String.valueOf(lastMessage.getBatteryLevel()));
            } else {
                mReceiveTimestampTextViewValue.setText(R.string.no_data);
                mRecordTimestampTextViewValue.setText(R.string.no_data);
                mRssiTextViewValue.setText(R.string.no_data);
                mBatteryTextViewValue.setText(R.string.no_data);
            }
        }
        return null;
    }
}

