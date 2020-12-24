package ru.dotdroid.telemetrictechdemo;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("sessionKey")
    private String mSessionKey;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("error")
    private error mError;
    @SerializedName("api")
    private api mApi;

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

    class api {
        @SerializedName("code")
        private int mCode;
        @SerializedName("url")
        private String mUrl;
        @SerializedName("delete")
        private String mDelete;

        public int getCode() {
            return mCode;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getDelete() {
            return mDelete;
        }
    }

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

    public api getApi() {
        return mApi;
    }

    public void setApi(api api) {
        mApi = api;
    }
}
