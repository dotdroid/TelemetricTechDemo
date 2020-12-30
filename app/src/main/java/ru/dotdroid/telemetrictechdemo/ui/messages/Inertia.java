package ru.dotdroid.telemetrictechdemo.ui.messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.List;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.DeviceMessage;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;
import ru.dotdroid.telemetrictechdemo.utils.MyDateFormat;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

public class Inertia extends Fragment {

    final private static String TAG = "Generic";

    private static final String ARG_DEVICE_ID = "device_id";
    private static final String ARG_MESSAGES_START = "messages_start";
    private static final String ARG_MESSAGES_END = "messages_end";

    private String mDeviceId;
    private long mMessagesStart, mMessagesEnd;

    private RecyclerView mMessagesRecyclerView;
    private MessagesAdapter mAdapter;

    public static Inertia newInstance(String deviceId, long start, long end) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE_ID, deviceId);
        args.putSerializable(ARG_MESSAGES_START, start);
        args.putSerializable(ARG_MESSAGES_END, end);
        Inertia fragment = new Inertia();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceId = (String) getArguments().getSerializable(ARG_DEVICE_ID);
        mMessagesStart = (Long) getArguments().getSerializable(ARG_MESSAGES_START);
        mMessagesEnd = (Long) getArguments().getSerializable(ARG_MESSAGES_END);

        new getMessages().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists_screen, container, false);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mMessagesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_container);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void updateUI() {
        DeviceLab deviceLab = DeviceLab.get(getActivity());
        List<DeviceMessage.Messages> messages = deviceLab.getMessages();

        if(mAdapter == null) {
            mAdapter = new MessagesAdapter(messages);
            mMessagesRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MessagesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DeviceMessage.Messages mMessage;

        private TextView mReceiveTimestampTextView, mReceiveTimestampTextViewValue,
                mRecordTimestampTextView, mRecordTimestampTextViewValue, mRssiTextView,
                mRssiTextViewValue, mBatteryTextView, mBatteryTextViewValue, mReasonTextView,
                mReasonTextViewValue, mAccelerometerAngleTextView, mAccelerometerAngleValueTextView,
                mMagnetometerAngleTextView, mMagnetometerAngleValueTextView;

        public MessagesHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_message_inertia, parent, false));
            itemView.setOnClickListener(this);

            mReceiveTimestampTextView = (TextView) itemView.findViewById(R.id.message_list_receive_timestamp);
            mReceiveTimestampTextViewValue = (TextView) itemView.findViewById(R.id.message_list_receive_timestamp_value);
            mRecordTimestampTextView = (TextView) itemView.findViewById(R.id.message_list_record_timestamp);
            mRecordTimestampTextViewValue = (TextView) itemView.findViewById(R.id.message_list_record_value);
            mRssiTextView = (TextView) itemView.findViewById(R.id.messages_list_rssi);
            mRssiTextViewValue = (TextView) itemView.findViewById(R.id.messages_list_rssi_value);
            mBatteryTextView = (TextView) itemView.findViewById(R.id.message_list_battery);
            mBatteryTextViewValue = (TextView) itemView.findViewById(R.id.message_list_battery_value);
            mReasonTextView = (TextView) itemView.findViewById(R.id.message_list_reason_to_send);
            mReasonTextViewValue = (TextView) itemView.findViewById(R.id.message_list_reason_to_send_value);
            mAccelerometerAngleTextView = (TextView) itemView.findViewById(R.id.message_list_acc_angle);
            mAccelerometerAngleValueTextView = (TextView) itemView.findViewById(R.id.message_list_acc_angle_value);
            mMagnetometerAngleTextView = (TextView) itemView.findViewById(R.id.message_list_mag_angle);
            mMagnetometerAngleValueTextView = (TextView) itemView.findViewById(R.id.message_list_mag_angle_value);
        }

        public void bind(DeviceMessage.Messages message) {
            mMessage = message;
            mReceiveTimestampTextView.setText(R.string.receive_timestamp);
            mRecordTimestampTextView.setText(R.string.record_timestamp);
            mRssiTextView.setText(R.string.rssi);
            mBatteryTextView.setText(R.string.battery);
            mReasonTextView.setText(R.string.reason_to_send);
            mAccelerometerAngleTextView.setText(R.string.accelerometer_angle);
            mMagnetometerAngleTextView.setText(R.string.magnetometer_angle);
            mReceiveTimestampTextViewValue.setText(MyDateFormat.unixToDate(mMessage.getDateTime()));
            mRecordTimestampTextViewValue.setText(MyDateFormat.unixToDate(mMessage.getRecordTimestamp2()));
            mRssiTextViewValue.setText(String.valueOf(mMessage.getLoRaRssi()));
            mBatteryTextViewValue.setText(String.valueOf(mMessage.getBatteryLevel2()));
            if (mMessage.getSignLinkButton() == 1) {
                mReasonTextViewValue.setText(R.string.link_button);
            } else if (mMessage.getImpactMagnetFlag() == 1) {
                mReasonTextViewValue.setText(R.string.magnet);
            } else if (mMessage.getSignAlarm() == 1) {
                mReasonTextViewValue.setText(R.string.alarm);
            } else {
                mReasonTextViewValue.setText(R.string.scheduled);
            }
            mAccelerometerAngleValueTextView.setText(String.valueOf(Math.round(mMessage.getAccelerometerAngle())));
            mMagnetometerAngleValueTextView.setText(String.valueOf(Math.round(mMessage.getMagnetometerAngle())));
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class MessagesAdapter extends RecyclerView.Adapter<MessagesHolder> {

        private final List<DeviceMessage.Messages> mMessages;

        public MessagesAdapter(List<DeviceMessage.Messages> messages) {
            mMessages = messages;
        }

        @Override
        public MessagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MessagesHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MessagesHolder holder, int position) {
            DeviceMessage.Messages message = mMessages.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }
    }

    private class getMessages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String messagesReceived = TelemetricApi.getMessages(mDeviceId, mMessagesStart, mMessagesEnd);

            Gson gson = new Gson();

            try {
                DeviceLab deviceLab = DeviceLab.get(getContext());
                List<DeviceMessage.Messages> messagesList = deviceLab.getMessages();
                messagesList.clear();

                DeviceMessage messages = gson.fromJson(messagesReceived, DeviceMessage.class);

                for(DeviceMessage.Messages m : messages.getMessages()) {
                    messagesList.add(m);
                }


            } catch (JsonParseException jpe) {
                Log.e(TAG, "Exception " + jpe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            updateUI();
        }
    }
}
