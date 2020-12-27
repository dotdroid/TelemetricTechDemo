package ru.dotdroid.telemetrictechdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dotdroid.telemetrictechdemo.R;

public class DeviceMessagesFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        return view;
    }
}
