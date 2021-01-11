package ru.dotdroid.telemetrictechdemo.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.json.Response;
import ru.dotdroid.telemetrictechdemo.ui.lastmessage.Generic;
import ru.dotdroid.telemetrictechdemo.ui.lastmessage.Inertia;
import ru.dotdroid.telemetrictechdemo.ui.lastmessage.Water;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;
import ru.dotdroid.telemetrictechdemo.utils.ErrorParse;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

public class DeviceFragment extends Fragment {

    public static final String EXTRA_DEVICE_EUI = "ru.dotdroid.telemetrictechdemo.device_eui";
    public static final String EXTRA_MESSAGES_START = "ru.dotdroid.telemetrictechdemo.messages_start";
    public static final String EXTRA_MESSAGES_END = "ru.dotdroid.telemetrictechdemo.messages_end";
    private static final String TAG = "DeviceFragment";
    private static final String ARG_DEVICE_EUI = "device_eui";

    private Device mDevice;
    private boolean mDelete = false;

    private long mMessagesAll, mMessagesStart, mMessagesEnd;

    private TextView mTitleTextView, mCreateDateTextView, mCreateDateTextViewValue, mTypeTextView,
            mDescriptionTextView, mDevEUITextTextView, mDevEUITextView, mAppKeyTextTextView,
            mAppKeyTextView, mLastMessageTextView;
    private Button mMessagesButton, mAllMessagesButton;
    private FrameLayout mLastMessageFragment;

    public static DeviceFragment newInstance(String deviceEui) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE_EUI, deviceEui);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_device, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_device:
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.delete_device)
                        .setMessage(R.string.confirm_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               new deleteDevice().execute();
                               dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewForFragment = inflater.inflate(R.layout.fragment_device, container, false);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mTitleTextView = (TextView) viewForFragment.findViewById(R.id.device_title);
        mTitleTextView.setText(mDevice.getTitle());
        mCreateDateTextView = (TextView) viewForFragment.findViewById(R.id.device_create_date);
        mCreateDateTextView.setText(R.string.created_at);
        mCreateDateTextViewValue = (TextView) viewForFragment.findViewById(R.id.device_create_date_value);
        mCreateDateTextViewValue.setText(mDevice.getCreatedAt());
        mTypeTextView = (TextView) viewForFragment.findViewById(R.id.device_type);
        mTypeTextView.setText(mDevice.getTypeTitle());
        mDescriptionTextView = (TextView) viewForFragment.findViewById(R.id.device_desc);
        mDescriptionTextView.setText(mDevice.getDesc());
        mDevEUITextTextView = (TextView) viewForFragment.findViewById(R.id.device_deveui_text);
        mDevEUITextTextView.setText(R.string.deveui);
        mDevEUITextView = (TextView) viewForFragment.findViewById(R.id.device_deveui);
        mDevEUITextView.setText(mDevice.getDeviceEui().toUpperCase());
        mAppKeyTextTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey_text);
        mAppKeyTextTextView.setText(R.string.app_key);
        mAppKeyTextView = (TextView) viewForFragment.findViewById(R.id.device_appkey);
        mAppKeyTextView.setText(mDevice.getKeyAp().toUpperCase());
        mLastMessageTextView = (TextView) viewForFragment.findViewById(R.id.device_last_message_text);
        mLastMessageTextView.setText(R.string.last_message);
        mLastMessageFragment = (FrameLayout) viewForFragment.findViewById(R.id.device_fragment_container);
        mMessagesButton = (Button) viewForFragment.findViewById(R.id.messages_button);
        mMessagesButton.setText(R.string.last_30days_messages);
        mMessagesAll = 1420070400L;
        mMessagesStart = (Calendar.getInstance().getTimeInMillis() / 1000L) - 2592000L;
        mMessagesEnd = Calendar.getInstance().getTimeInMillis() / 1000L;
        mMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeviceMessagesActivity.newIntent(getContext(), mDevice.getDeviceEui(), mMessagesStart, mMessagesEnd);
                startActivity(intent);
            }
        });
        mAllMessagesButton = (Button) viewForFragment.findViewById(R.id.messages_button_all);
        mAllMessagesButton.setText(R.string.all_messages);
        mAllMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeviceMessagesActivity.newIntent(getContext(), mDevice.getDeviceEui(), mMessagesAll, mMessagesEnd);
                startActivity(intent);
            }
        });

        return viewForFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Fragment childFragment;

        if (mDevice.getDeviceTypeId().equals("66")) {
            childFragment = Water.newInstance(ARG_DEVICE_EUI);
        } else if (mDevice.getDeviceTypeId().equals("82")) {
            childFragment = Inertia.newInstance(ARG_DEVICE_EUI);
        } else {
            childFragment = Generic.newInstance(ARG_DEVICE_EUI);
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.device_fragment_container, childFragment).commit();
    }

    private class deleteDevice extends AsyncTask<Void, Void, Void> {

        private int mError = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String responseDelete = TelemetricApi.deleteDevice(getContext(), mDevice.getId());

            Gson gson = new Gson();
            Response response = gson.fromJson(responseDelete, Response.class);

            if(response.getError() == null) {
                mError = 0;
            } else {
                mError = response.getError().getCode();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                getActivity().finish();
            if(mError == 0) {
                getActivity().finish();
            } else {
                ErrorParse.errorToast(getContext(), mError);
            }
        }
    }
}
