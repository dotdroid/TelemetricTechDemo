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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginScreenActivity extends AppCompatActivity {

    private TextView mEmailTextView, mPasswordTextView;
    private EditText mEmailTextViewField, mPasswordTextViewField;
    private Button mLogInButton;
    private String mEmail, mPassword;
    protected static String sSessionKey = "";


    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mEmailTextView = findViewById(R.id.login_email_text);
        mEmailTextView.setText(R.string.email);
        mPasswordTextView = findViewById(R.id.login_password_text);
        mPasswordTextView.setText(R.string.password);

        mEmailTextViewField = findViewById(R.id.login_email_field);
        mEmailTextViewField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEmail = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mPasswordTextViewField = findViewById(R.id.login_password_field);
        mPasswordTextViewField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLogInButton = findViewById(R.id.login_button);
        mLogInButton.setText("Log in");
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new connectToUrl().execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sSessionKey = "";
    }

    private class connectToUrl extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String md5Auth = new Md5Util().md5Custom(mEmail + mPassword);
                Map<String, String> postData = new HashMap<>();
                postData.put("authKey", md5Auth);
                PostParamBuild postDataBuild = new PostParamBuild();

                byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
                int postDataBytesLen = postDataBytes.length;

                String result = new SendPost()
                        .sendPostString("https://dev.telemetric.tech/api.login",
                                postDataBytes, "",
                                String.valueOf(postDataBytesLen));

                Response login = new Gson().fromJson(result, Response.class);

                if(login.getError() == null) sSessionKey = login.getSessionKey();

            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(sSessionKey != "") {
                Intent intent = new Intent(LoginScreenActivity.this, DeviceListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.incorrect_pwd,Toast.LENGTH_SHORT).show();
            }
        }
    }
}