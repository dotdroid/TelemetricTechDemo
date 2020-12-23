package ru.dotdroid.telemetrictechdemo;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("sessionKey")
    private String mSessionKey;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("error")
    private error mError;

    public String getSessionKey() {
        return mSessionKey;
    }

    public void setSessionKey(String sessionKey) {
        mSessionKey = sessionKey;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public error getError() {
        return mError;
    }

    public void setError(error error) {
        mError = error;
    }

    class error{
        @SerializedName("code")
        private String mCode;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("logout")
        private String mLogout;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        public String getLogout() {
            return mLogout;
        }

        public void setLogout(String logout) {
            mLogout = logout;
        }
    }
}
