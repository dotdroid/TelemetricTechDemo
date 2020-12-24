package ru.dotdroid.telemetrictechdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dotdroid.telemetrictechdemo.devices.Device;
import ru.dotdroid.telemetrictechdemo.devices.DeviceLab;
import ru.dotdroid.telemetrictechdemo.devices.DeviceType;

public class DeviceCreateFragment extends Fragment {

    private static final String TAG = "CreateDeviceFragment";
    private static final String EXTRA_DEVEUI = "ru.dotdroid.telemetrictechdemo.deviceEui";
    private static final int REQUEST_DEVEUI = 0;

    private List<DeviceType.types> mDeviceTypes;

    private String mDeviceEui = "", mDeviceTitle = "", mDeviceDesc = "", mDeviceType = "", mDeviceTypeTitle = "", mDeviceKeyApp = "";

    TextView mCreateDeviceText, mCreateName, mCreateDevEui, mCreateAppKey,  mCreateDesc,  mCreateDeviceType;
    EditText mCreateNameField, mCreateDevEuiField, mCreateAppKeyField, mCreateDescField;
    Button mCreateDevice;
    Spinner mCreateDeviceTypeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_device, container, false);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        DeviceLab deviceLab = DeviceLab.get(getActivity());
        mDeviceTypes = deviceLab.getDeviceTypes();

        mCreateDeviceText = (TextView) view.findViewById(R.id.create_device_text);
        mCreateDeviceText.setText(R.string.create_device_text);
        mCreateName = (TextView) view.findViewById(R.id.create_name_text);
        mCreateName.setText(R.string.create_name);
        mCreateDevEui = (TextView) view.findViewById(R.id.create_deveui_text);
        mCreateDevEui.setText(R.string.deveui);
        mCreateAppKey = (TextView) view.findViewById(R.id.create_appkey);
        mCreateAppKey.setText(R.string.app_key);
        mCreateDesc = (TextView) view.findViewById(R.id.create_desc);
        mCreateDesc.setText(R.string.create_desc);
        mCreateDeviceType = (TextView) view.findViewById(R.id.create_device_type);
        mCreateDeviceType.setText(R.string.create_device_type);

        mCreateNameField = (EditText) view.findViewById(R.id.create_name_field);
        mCreateNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDeviceTitle = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCreateDevEuiField = (EditText) view.findViewById(R.id.create_deveui_field);
        mCreateDevEuiField.setText(mDeviceEui);
        mCreateDevEuiField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDeviceEui = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCreateAppKeyField = (EditText) view.findViewById(R.id.create_appkey_field);
        mCreateAppKeyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDeviceKeyApp = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCreateDescField = (EditText) view.findViewById(R.id.create_desc_field);
        mCreateDescField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDeviceDesc = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCreateDevice = (Button) view.findViewById(R.id.create_device_button);
        mCreateDevice.setText(R.string.create_device_button);
        mCreateDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new createDevice().execute();
            }
        });


        mCreateDeviceTypeSpinner = (Spinner) view.findViewById(R.id.create_device_type_spinner);
        mCreateDeviceTypeSpinner.setEnabled(false);
        new getAllDevicesTypes().execute();
        mCreateDeviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mDeviceType = mDeviceTypes.get(position).getId();
                mDeviceTypeTitle = mDeviceTypes.get(position).getTitle();
                mCreateDeviceTypeSpinner.setTextAlignment(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_create_device, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_button:
                Intent intent = new Intent(getActivity(), CameraScreenActivity.class);
                startActivityForResult(intent, REQUEST_DEVEUI);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, String.valueOf(resultCode));

        if(resultCode == Activity.RESULT_OK) {
            String devEui = data.getStringExtra(EXTRA_DEVEUI);
            mDeviceEui = devEui;
            mCreateDevEuiField.setText(devEui);
        } else {
            Toast.makeText(getContext(), "DevEUI not found", Toast.LENGTH_SHORT).show();
        }
    }

    private class getAllDevicesTypes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Map<String, String> postData = new HashMap<>();
                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
                int postDataBytesLen = postDataBytes.length;

                String result = new SendPost()
                        .sendPostString("https://dev.telemetric.tech/api.devices.types",
                                postDataBytes, LoginScreenActivity.sSessionKey,
                                String.valueOf(postDataBytesLen));

                Gson gson = new Gson();

                DeviceType devices = gson.fromJson(result, DeviceType.class);

                DeviceLab deviceLab = DeviceLab.get(getActivity());
                List<DeviceType.types> devicesList = deviceLab.getDeviceTypes();

                for(DeviceType.types d : devices.getDevices().getTypes()) {
                    d.getTitle();
                    d.getId();
                    devicesList.add(d);
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArrayAdapter<DeviceType.types> adapter = new ArrayAdapter<DeviceType.types>(getActivity(), android.R.layout.simple_spinner_item, mDeviceTypes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCreateDeviceTypeSpinner.setAdapter(adapter);
            mCreateDeviceTypeSpinner.setEnabled(true);
            mCreateDeviceTypeSpinner.setSelection(0);
        }
    }

    private class createDevice extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Map<String, String> postData = new HashMap<>();
                postData.put("deviceID", mDeviceEui);
                postData.put("deviceGateway", "");
                postData.put("gatewayID", "");
                postData.put("inside_addr", "");
                postData.put("deviceTitle", mDeviceTitle);
                postData.put("deviceDesc", mDeviceDesc);
                postData.put("deviceType", mDeviceType);
                postData.put("keyAp", mDeviceKeyApp);
                postData.put("keyNw", "");
                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
                int postDataBytesLen = postDataBytes.length;

                String result = new SendPost()
                        .sendPostString("https://dev.telemetric.tech/api.devices.create",
                                postDataBytes, LoginScreenActivity.sSessionKey,
                                String.valueOf(postDataBytesLen));

                Device d = new Device();
                DeviceLab deviceLab = DeviceLab.get(getActivity());
                List<Device> devicesList = deviceLab.getDevices();

                d.setTitle(mDeviceTitle);
                d.setDeviceEui(mDeviceEui);
                d.setKeyAp(mDeviceKeyApp);
                d.setTypeTitle(mDeviceTypeTitle);
                d.setDesc(mDeviceDesc);
                d.setDeviceTypeId(mDeviceType);
                d.setCreatedAt(new Date().toString());
                devicesList.add(0, d);

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            getActivity().finish();
        }
    }
}
