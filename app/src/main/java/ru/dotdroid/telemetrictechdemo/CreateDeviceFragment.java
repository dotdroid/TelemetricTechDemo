package ru.dotdroid.telemetrictechdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dotdroid.telemetrictechdemo.devices.DeviceLab;
import ru.dotdroid.telemetrictechdemo.devices.DeviceType;
import ru.dotdroid.telemetrictechdemo.devices.DeviceTypes;

public class CreateDeviceFragment extends Fragment {

    private static final String TAG = "CreateDeviceFragment";

    private List<DeviceType> mDeviceTypes;

    TextView mCreateDeviceText, mCreateName, mCreateDevEui, mCreateAppKey,  mCreateDesc,  mCreateDeviceType;
    EditText mCreateNameField, mCreateDevEuiField, mCreateAppKeyField, mCreateDescField;
    Button mCreateQR;
    Spinner mCreateDeviceTypeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_device, container, false);

        new getAllDevicesTypes().execute();

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
        mCreateDevEuiField = (EditText) view.findViewById(R.id.create_deveui_field);
        mCreateAppKeyField = (EditText) view.findViewById(R.id.create_appkey_field);
        mCreateDescField = (EditText) view.findViewById(R.id.create_desc_field);

        mCreateQR = (Button) view.findViewById(R.id.create_scan_qr);
        mCreateQR.setText(R.string.scan_qr);

        mCreateDeviceTypeSpinner = (Spinner) view.findViewById(R.id.create_device_type_spinner);
        mCreateDeviceTypeSpinner.setEnabled(false);

//        String[] mDeviceTypesTitle = new String[mDeviceTypes.size()];
//
//        for(int i=0; i<mDeviceTypes.size(); i++) {
//
//            mDeviceTypes.toArray(mDeviceTypesTitle);
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , mDeviceTypesTitle);



        return view;
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

                String cropresult = result.substring(20, result.length() - 2);

//                Log.i(TAG, cropresult);


                Gson gson = new Gson();

                DeviceType[] devices = gson.fromJson(cropresult, DeviceType[].class);

                DeviceLab deviceLab = DeviceLab.get(getActivity());
                List<DeviceType> devicesTypes = deviceLab.getDeviceTypes();

                for(DeviceType d : devices) {
                    d.getTitle();
                    d.getId();
                    devicesTypes.add(d);
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mCreateDeviceTypeSpinner.setEnabled(true);
        }
    }
}
