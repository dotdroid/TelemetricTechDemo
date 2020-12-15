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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchScreen extends AppCompatActivity {

    private static final String TAG = "SearchScreen";

    TextView devEUITextView, enterDevEUITextView, searchResultTextView;
    Button searchButton, cameraButton;
    ImageButton clearDevEUIField;

    String devEUI = "";
    static String searchResult;
    String searchResultRaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        devEUITextView = (TextView) findViewById(R.id.devEUI);
        devEUITextView.setText("DevEUI");

        enterDevEUITextView = (TextView) findViewById(R.id.enterDevEUI);
        enterDevEUITextView.setText(devEUI);
        enterDevEUITextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                devEUI = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                devEUI = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                devEUI = s.toString();
            }
        });

        clearDevEUIField = (ImageButton) this.findViewById(R.id.clearDevEUI);
        clearDevEUIField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterDevEUITextView.setText("");
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

                if(searchResultRaw.length() < 5) {
                    Toast.makeText(SearchScreen.this, "DevEUI not found", Toast.LENGTH_SHORT).show();
                }

                finish();
                startActivity(getIntent());
            }
        });

        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setText("Scan QR");
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraScreen.class);
                startActivity(intent);
            }
        });
    }

    private class sendSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Map<String, String> postData = new HashMap<>();
                postData.put("search", devEUI);

                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.POSTParBuilder(postData).getBytes();

                String result = new SendPostTelemetric()
                        .sendPostString("https://dev.telemetric.tech/api.devices.search",
                                postDataBytes, LoginScreen.sessionKey,
                                String.valueOf(postDataBytes.length));
                Log.i(TAG, "Fetched contents of Url: " + result);

                searchResultRaw = result;

                StringBuilder builder = new StringBuilder();

                try {
                    JSONArray ja = new JSONArray(result);

                    for(int i=0; i < ja.length(); i++) {
                        String jaLastMessage = ja.getJSONObject(i).getString("last_message");
                        JSONObject joLastMessage = new JSONObject(jaLastMessage);

                        builder.append(ja.getJSONObject(i).getString("title"));
                        builder.append("\n");
                        builder.append(ja.getJSONObject(i).getString("deviceID"));
                        builder.append("\n");
                        builder.append(ja.getJSONObject(i).getString("keyAp"));
                        builder.append("\n");
                        builder.append("Last message:");
                        builder.append("\n");
                        builder.append("RSSI: ");
                        builder.append(joLastMessage.getInt("loRaRSSI"));
                        builder.append("\n");
                    }
                    searchResult = String.valueOf(builder);
                } catch (JSONException e) {
                    Log.e(TAG, "Couldn't find item " + e);
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}