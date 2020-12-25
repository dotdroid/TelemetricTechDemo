package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.utils.ErrorParse;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

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
        mLogInButton.setOnClickListener(v -> {
            if(validateField()) {
                new sendLogin().execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sSessionKey = "";
    }

    public boolean validateField() {
        boolean valid = true;

        if (mEmail == null || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEmailTextViewField.setError("Enter a valid email address");
            valid = false;
        } else {
            mEmailTextViewField.setError(null);
        }

        if (mPassword == null || mPassword.length() < 6) {
            mPasswordTextViewField.setError("Password must be 6 or more characters");
            valid = false;
        } else {
            mPasswordTextViewField.setError(null);
        }

        return valid;
    }

    private class sendLogin extends AsyncTask<Void, Void, Void> {

        private int mError = 0;

        @Override
        protected Void doInBackground(Void... params) {
            int responseLogin = TelemetricApi.sendLogin(mEmail, mPassword);
            mError = responseLogin;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(mError == 0) {
                Intent intent = new Intent(LoginScreenActivity.this, DeviceListActivity.class);
                startActivity(intent);
                finish();
            } else {
                ErrorParse.errorToast(LoginScreenActivity.this, mError);
            }
        }
    }
}