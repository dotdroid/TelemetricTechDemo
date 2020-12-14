package ru.dotdroid.telemetrictechdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginScreen extends AppCompatActivity {

    TextView emailTv, passTv, emailTextTV, passTextTV;
    Button signInButton;
    String email, password;
    String loginResult;
    static String sessionKey;
    static String searchResult;

    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        emailTextTV = findViewById(R.id.emailText);
        emailTextTV.setText("Email");
        emailTv = findViewById(R.id.editTextTextEmailAddress);
        emailTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passTextTV = findViewById(R.id.passText);
        passTextTV.setText("Password");
        passTv = findViewById(R.id.editTextTextPassword);
        passTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInButton = findViewById(R.id.button);
        signInButton.setText("Log in");
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new connectToUrl().execute();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(loginResult == "true") {
                    Intent intent = new Intent(LoginScreen.this, SearchScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginScreen.this, R.string.incorrect_pwd, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class connectToUrl extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String md5Auth = new Md5Util().md5Custom(email+password);
                String result = new TelemetricTechMethods()
                        .sendLoginString("https://dev.telemetric.tech/api.login", md5Auth);
                Log.i(TAG, "Fetched contents of Url: " + result);

                try {
                    JSONObject jo = new JSONObject(result);
                    loginResult = jo.getString("login");
                    sessionKey = jo.getString("sessionKey");
                } catch (JSONException jse) {
                    Log.e(TAG, "Failed to find login key " + jse);
                }

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}