package ru.dotdroid.telemetrictechdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dotdroid.telemetrictechdemo.devices.Device;
import ru.dotdroid.telemetrictechdemo.devices.DeviceLab;

public class MainScreenFragment extends Fragment {

    final private static String TAG = "MainScreenFragment";

    private RecyclerView mAllDevicesRecyclerView;
    private DeviceAdapter mAdapter;
    private int mLastUpdatedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        if(LoginScreenActivity.sSessionKey.equals("")) {
            getActivity().finish();
            Intent intent = new Intent(getContext(), LoginScreenActivity.class);
            startActivity(intent);
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mAllDevicesRecyclerView = (RecyclerView) view.findViewById(R.id.allDevicesList);
        mAllDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new getAllDevices().execute();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_screen, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_device:
                Intent intentCreate = new Intent(getActivity(), CreateDeviceActivity.class);
                startActivity(intentCreate);
                return true;
            case R.id.logout:
                Intent intentLogout = new Intent(getActivity(), LoginScreenActivity.class);
                startActivity(intentLogout);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        DeviceLab deviceLab = DeviceLab.get(getActivity());
        List<Device> devices = deviceLab.getDevices();

        if(mAdapter == null) {
            mAdapter = new DeviceAdapter(devices);
            mAllDevicesRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(mLastUpdatedPosition);
        }

        mAdapter = new DeviceAdapter(devices);
        mAllDevicesRecyclerView.setAdapter(mAdapter);
    }

    private class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTextView, mDevEUITextView;
        private final ImageView mImageView;

        private Device mDevice;

        public DeviceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_item_device, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDevEUITextView = (TextView) itemView.findViewById(R.id.list_item_deveui);
            mImageView = (ImageView) itemView.findViewById(R.id.list_device_image);
        }

        public void bind(Device device) {
            mDevice = device;
            mTitleTextView.setText(mDevice.getTitle());
            mDevEUITextView.setText(mDevice.getDeviceEui());
            if(mDevice.getDeviceTypeId().equals("17")){
                mImageView.setImageResource(R.drawable.electro);
            } else if(mDevice.getDeviceTypeId().equals("53")) {
                mImageView.setImageResource(R.drawable.baza);
            } else if(mDevice.getDeviceTypeId().equals("66")) {
                mImageView.setImageResource(R.drawable.water);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = DevicePagerActivity.newIntent(getActivity(), mDevice.getDeviceEui());
            mLastUpdatedPosition = this.getAdapterPosition();
            startActivity(intent);
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
                                postDataBytes, LoginScreenActivity.sSessionKey,
                                String.valueOf(postDataBytesLen));

                Gson gson = new Gson();

                Device[] devices = gson.fromJson(result, Device[].class);

                DeviceLab deviceLab = DeviceLab.get(getActivity());
                List<Device> devicesList = deviceLab.getDevices();

                for(Device d : devices) {
                    d.getTitle();
                    d.getDeviceEui();
                    d.getKeyAp();
                    d.getTypeTitle();
                    d.getDesc();
                    d.getCreatedAt();
                    d.getLastMessage();
                    d.getLastActive();
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
            updateUI();
        }
    }
}
