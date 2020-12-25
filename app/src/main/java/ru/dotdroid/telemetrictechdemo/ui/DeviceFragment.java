package ru.dotdroid.telemetrictechdemo.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;

import static ru.dotdroid.telemetrictechdemo.utils.TelemetricApi.*;

public class DeviceFragment extends Fragment {

    private static final String TAG = "DeviceFragment";

    private static final String ARG_DEVICE_EUI = "device_eui";

    private Device mDevice;
    private boolean mDelete = false;

    private TextView mTitleTextView, mCreateDateTextView, mTypeTextView,
            mDevEUITextTextView, mDevEUITextView, mAppKeyTextTextView, mAppKeyTextView,
            mDescriptionTextView, mLastMessageTextView;
    private Button mMessagesButton;

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
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.confirm_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               new deleteDevice().execute();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
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

        mMessagesButton = (Button) viewForFragment.findViewById(R.id.messages_button);
        mMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeviceMessagesActivity.class);
                startActivity(intent);
            }
        });

        return viewForFragment;
    }

    private class deleteDevice extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            deleteDevice(getContext(), mDevice.getId());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
                getActivity().finish();
        }
    }
}
