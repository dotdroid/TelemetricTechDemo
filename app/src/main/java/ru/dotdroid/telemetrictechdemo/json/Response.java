package ru.dotdroid.telemetrictechdemo.json;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("sessionKey")
    private String mSessionKey;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("error")
    private Error mError;
    @SerializedName("api")
    private Api mApi;
    @SerializedName("result")
    private Result mResult;

    public class Error{
        @SerializedName("code")
        private int mCode;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("logout")
        private String mLogout;

        public int getCode() {
            return mCode;
        }

        public String getMessage() {
            return mMessage;
        }

        public String getLogout() {
            return mLogout;
        }
    }

    public class Api {
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

     public class Result {
        @SerializedName("code")
        private int mCode;
        @SerializedName("response")
        private CreateResponseMessage mResponse;
        @SerializedName("url")
        public String mUrl;
        @SerializedName("method")
        public String mId;
        @SerializedName("newId")
        public String mNewId;

        public int getCode() {
            return mCode;
        }

        public CreateResponseMessage getResponse() {
            return mResponse;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getId() {
            return mId;
        }

        public String getNewId() {
            return mNewId;
        }

         public void setApi(Api api) {
             mApi = api;
         }
     }

    public class CreateResponseMessage {
        @SerializedName("applicationID")
        public String mApplicationID;
        @SerializedName("devEUI")
        public String mDevEui;
        @SerializedName("name")
        public String mName;
        @SerializedName("description")
        public String mDescription;
        @SerializedName("deviceProfileID")
        public String mDeviceProfileID;
        @SerializedName("referenceAltitude")
        public String mReferenceAltitude;
        @SerializedName("skipFCntCheck")
        public String mSkipFCntCheck;

        public String getApplicationID() {
            return mApplicationID;
        }

        public String getDevEui() {
            return mDevEui;
        }

        public String getName() {
            return mName;
        }

        public String getDescription() {
            return mDescription;
        }

        public String getDeviceProfileID() {
            return mDeviceProfileID;
        }

        public String getReferenceAltitude() {
            return mReferenceAltitude;
        }

        public String getSkipFCntCheck() {
            return mSkipFCntCheck;
        }
    }

    public String getSessionKey() {
        return mSessionKey;
    }

    public String getLogin() {
        return mLogin;
    }

    public Error getError() {
        return mError;
    }

    public Api getApi() {
        return mApi;
    }

    public Result getResult() {
        return mResult;
    }
}
