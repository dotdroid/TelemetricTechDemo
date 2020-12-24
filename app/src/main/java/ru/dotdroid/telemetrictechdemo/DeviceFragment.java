package ru.dotdroid.telemetrictechdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dotdroid.telemetrictechdemo.devices.Device;
import ru.dotdroid.telemetrictechdemo.devices.DeviceLab;

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

            try {
                Map<String, String> postData = new HashMap<>();
                postData.put("deviceId", mDevice.getId());
                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
                int postDataBytesLen = postDataBytes.length;

                String result = new SendPost()
                        .sendPostString("https://dev.telemetric.tech/api.devices.remove",
                                postDataBytes, LoginScreenActivity.sSessionKey,
                                String.valueOf(postDataBytesLen));

                Gson gson = new Gson();

                Response response = gson.fromJson(result, Response.class);

                Log.i(TAG, response.getApi().getDelete());

//                if(response.getApi().getDelete().equals("DELETE")) mDelete = true;

                Log.i(TAG, response.toString());

                DeviceLab deviceLab = DeviceLab.get(getActivity());
                List<Device> devicesList = deviceLab.getDevices();
                devicesList.clear();

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



            if(mDelete) {
                try{
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.e(TAG, "Error" + e);
                }
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
