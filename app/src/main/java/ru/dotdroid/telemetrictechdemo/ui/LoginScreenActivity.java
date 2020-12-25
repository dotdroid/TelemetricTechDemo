package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

import static ru.dotdroid.telemetrictechdemo.utils.TelemetricApi.*;

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
            sendLogin(mEmail, mPassword);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(TelemetricApi.sSessionKey != "") {
                Intent intent = new Intent(LoginScreenActivity.this, DeviceListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.incorrect_pwd,Toast.LENGTH_SHORT).show();
            }
        }
    }
}