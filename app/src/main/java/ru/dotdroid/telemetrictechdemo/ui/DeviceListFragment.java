package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.utils.DeviceLab;
import ru.dotdroid.telemetrictechdemo.utils.MyDateFormat;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

public class DeviceListFragment extends Fragment {

    final private static String TAG = "DeviceListFragment";

    private RecyclerView mAllDevicesRecyclerView;
    private DeviceAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists_screen, container, false);

        mAllDevicesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_container);
        mAllDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new getAllDevices().execute();

        Log.i(TAG, "Return to devices list");

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_device_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                new getAllDevices().execute();
                updateUI();
                return true;
            case R.id.create_device:
                Intent intentCreate = new Intent(getActivity(), DeviceCreateActivity.class);
                startActivity(intentCreate);
                return true;
            case R.id.logout:
                Intent intentLogout = new Intent(getActivity(), LoginScreenActivity.class);
                startActivity(intentLogout);
                getActivity().finish();
                DeviceLab deviceLab = DeviceLab.get(getActivity());
                deviceLab.clearLists();
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
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTextView, mDevEUITextView, mLastActiveValueTextView;
        private final ImageView mDeviceImageView, mStatusImageView;

        private Device mDevice;

        public DeviceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_list_item_device, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDevEUITextView = (TextView) itemView.findViewById(R.id.list_item_deveui);
            mLastActiveValueTextView = (TextView) itemView.findViewById(R.id.list_item_last_active);
            mDeviceImageView = (ImageView) itemView.findViewById(R.id.list_device_image);
            mStatusImageView = (ImageView) itemView.findViewById(R.id.list_online_indicator);
        }

        public void bind(Device device) {
            mDevice = device;
            long currentTime = Calendar.getInstance().getTimeInMillis() / 1000L;
            mTitleTextView.setText(mDevice.getTitle());
            mDevEUITextView.setText(mDevice.getDeviceEui().toUpperCase());
            long shortDate = Long.parseLong(mDevice.getLastActive());
            String shortDateStr = MyDateFormat.unixToShortDate(shortDate);
            mLastActiveValueTextView.setText(shortDateStr);

            if(mDevice.getDeviceTypeId().equals("17")){
                mDeviceImageView.setImageResource(R.drawable.icon_power);
            } else if(mDevice.getDeviceTypeId().equals("53")) {
                mDeviceImageView.setImageResource(R.drawable.icon_base);
            } else if(mDevice.getDeviceTypeId().equals("66")) {
                mDeviceImageView.setImageResource(R.drawable.icon_water);
            } else {
                mDeviceImageView.setImageResource(R.drawable.icon_generic);
            }

            if((currentTime - Long.parseLong(mDevice.getLastActive()) < Long.parseLong(mDevice.getReportPeriodUpdate()))) {
                mStatusImageView.setImageResource(R.drawable.status_online);
            } else if (Long.parseLong(mDevice.getLastActive()) == 0) {
                mStatusImageView.setImageResource(R.drawable.status_new);
            } else {
                mStatusImageView.setImageResource(R.drawable.status_offline);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = DevicePagerActivity.newIntent(getActivity(), mDevice.getDeviceEui());
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
            TelemetricApi.getDevices(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            updateUI();
        }
    }
}
