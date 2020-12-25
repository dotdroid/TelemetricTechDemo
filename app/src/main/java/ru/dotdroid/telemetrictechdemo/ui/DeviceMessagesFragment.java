package ru.dotdroid.telemetrictechdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dotdroid.telemetrictechdemo.R;

public class DeviceMessagesFragment extends Fragment {

    public static final String EXTRA_DEVICE_EUI = "ru.dotdroid.telemetrictechdemo.device_eui";
    public static final String EXTRA_DEVICE_TYPE_ID = "ru.dotdroid.telemetrictechdemo.device_type_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        return view;
    }
}
