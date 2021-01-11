package ru.dotdroid.telemetrictechdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

import ru.dotdroid.telemetrictechdemo.R;
import ru.dotdroid.telemetrictechdemo.utils.ErrorParse;
import ru.dotdroid.telemetrictechdemo.utils.TelemetricApi;

public class LoginScreenActivity extends AppCompatActivity {

    private TextInputLayout mEmailFieldLayout, mPasswordFieldLayout;
    private Button mLogInButton;
    private String mEmail = "", mPassword = "";
    private boolean mEmailValid, mPasswordValid;

    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mEmailFieldLayout = (TextInputLayout) findViewById(R.id.login_email_layout);
        mEmailFieldLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                mEmail = text.toString();
            }

            @Override
            public void afterTextChanged(Editable text) {
            }
        });

        mPasswordFieldLayout = (TextInputLayout) findViewById(R.id.login_password_layout);
        mPasswordFieldLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                mPassword = text.toString();
            }

            @Override
            public void afterTextChanged(Editable text) {

            }
        });

        mLogInButton = (Button) findViewById(R.id.login_button);
        mLogInButton.setText(R.string.login);
        mLogInButton.setOnClickListener(v -> {
            validate();
            if(mEmailValid && mPasswordValid) {
                new sendLogin().execute();
            }
        });
    }

    private void validate() {
        if (mPassword.equals("")) {
            mEmailFieldLayout.setError("Email is required");
            mEmailFieldLayout.setErrorEnabled(true);
            mEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches() ) {
            mEmailFieldLayout.setError("Please enter a valid email address");
            mEmailFieldLayout.setErrorEnabled(true);
            mEmailValid = false;
        } else {
            mEmailFieldLayout.setErrorEnabled(false);
            mEmailValid = true;
        }

        if (mPassword.equals("")) {
            mPasswordFieldLayout.setError("Password is required");
            mPasswordFieldLayout.setErrorEnabled(true);
            mPasswordValid = false;
        } else if (mPassword.length() < 5 ) {
            mPasswordFieldLayout.setError("Password is required and length must be >= 5");
            mPasswordFieldLayout.setErrorEnabled(true);
            mPasswordValid = false;
        } else {
            mPasswordFieldLayout.setErrorEnabled(false);
            mPasswordValid = true;
        }
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