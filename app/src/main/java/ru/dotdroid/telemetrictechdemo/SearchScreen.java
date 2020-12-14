package ru.dotdroid.telemetrictechdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SearchScreen extends AppCompatActivity {

    private static final String TAG = "SearchScreen";

    TextView devEUITextView, enterDevEUITextView, searchResultTextView;
    Button searchButton;

    String devEUI;
    static String searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        devEUITextView = (TextView) findViewById(R.id.devEUI);
        devEUITextView.setText("DevEUI");

        enterDevEUITextView = (TextView) findViewById(R.id.enterDevEUI);
        enterDevEUITextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                devEUI = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchResultTextView = (TextView) findViewById(R.id.searchResult);
        searchResultTextView.setText(searchResult);

        searchButton = (Button) findViewById(R.id.searchByDevEUI);
        searchButton.setText("Find device");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new sendSearch().execute();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
                startActivity(getIntent());
            }
        });


    }

    private class sendSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String result = new TelemetricTechMethods()
                        .searchByDevEUIString("https://dev.telemetric.tech/api.devices.search", devEUI);
                Log.i(TAG, "Fetched contents of Url: " + result);

                searchResult = result;

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}