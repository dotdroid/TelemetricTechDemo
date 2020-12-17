package ru.dotdroid.telemetrictechdemo;

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

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreenFragment extends Fragment {

    final private static String TAG = "MainScreenFragment";
    protected static JSONArray sAllDevicesJSONArray;

    private RecyclerView mAllDevicesRecyclerView;
    private DeviceAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        mAllDevicesRecyclerView = (RecyclerView) view.findViewById(R.id.allDevicesList);
        mAllDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new getAllDevices().execute();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateUI();

        return view;
    }

    private void updateUI() {
        DeviceLab deviceLab = DeviceLab.get(getActivity());
        List<Device> devices = deviceLab.getDevices();

        mAdapter = new DeviceAdapter(devices);
        mAllDevicesRecyclerView.setAdapter(mAdapter);
    }

    private class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTextView;
        private final TextView mDevEUITextView;

        private Device mDevice;

        public DeviceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_device, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDevEUITextView = (TextView) itemView.findViewById(R.id.list_item_deveui);
        }

        public void bind(Device device) {
            mDevice = device;
            mTitleTextView.setText(mDevice.getTitle());
            mDevEUITextView.setText(mDevice.getDevEUI());
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class DeviceAdapter extends RecyclerView.Adapter<DeviceHolder> {
        private final List<Device> mDevices;

        public DeviceAdapter(List<Device> devices) {
            mDevices = devices;
        }

        @Override
        public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DeviceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DeviceHolder holder, int position) {
            Device device = mDevices.get(position);
            holder.bind(device);
        }

        @Override
        public int getItemCount() {
            return mDevices.size();
        }
    }

    private class getAllDevices extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Map<String, String> postData = new HashMap<>();
                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
                int postDataBytesLen = postDataBytes.length;

                String result = new SendPost()
                        .sendPostString("https://dev.telemetric.tech/api.devices.all",
                                postDataBytes, LoginScreen.sSessionKey,
                                String.valueOf(postDataBytesLen));

                try {
                    sAllDevicesJSONArray = new JSONArray(result);
                } catch (JSONException jse) {
                    Log.e(TAG, "JSONArray exception: " + jse);
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}
