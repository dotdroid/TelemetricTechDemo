package ru.dotdroid.telemetrictechdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dotdroid.telemetrictechdemo.json.Response;
import ru.dotdroid.telemetrictechdemo.json.Device;
import ru.dotdroid.telemetrictechdemo.json.DeviceMessage;
import ru.dotdroid.telemetrictechdemo.json.DeviceType;
import ru.dotdroid.telemetrictechdemo.ui.LoginScreenActivity;

public class TelemetricApi {

    private static final String TAG = "TelemetricApi";

    private static final String TELEMETRIC_URL = "https://dev.telemetric.tech/";

    private static String sSessionKey;

    public static int sendLogin(String email, String password) {

        int mError = 0;

        try {
            String md5Auth = new Md5Util().md5Custom(email + password);
            Map<String, String> postData = new HashMap<>();
            postData.put("authKey", md5Auth);
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.login",
                            postDataBytes, "",
                            String.valueOf(postDataBytesLen));

            Response login = new Gson().fromJson(result, Response.class);

            if(login.getError() == null) {
                sSessionKey = login.getSessionKey();
            } else {
                mError = login.getError().getCode();
            }

        } catch (
                IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }
        return mError;
    }

    public static void getDevices(Context context) {

        try {
            Map<String, String> postData = new HashMap<>();
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.devices.all",
                            postDataBytes, sSessionKey,
                            String.valueOf(postDataBytesLen));

            Log.i(TAG, "Download list");

            Gson gson = new Gson();

            try {
                DeviceLab deviceLab = DeviceLab.get(context);
                List<Device> devicesList = deviceLab.getDevices();
                devicesList.clear();
                Device[] devices = gson.fromJson(result, Device[].class);

                for(Device d : devices) {
                    d.getTitle();
                    d.getDeviceEui();
                    d.getKeyAp();
                    d.getTypeTitle();
                    d.getDesc();
                    d.getCreatedAt();
                    d.getLastMessage();
                    d.getLastActive();
                    devicesList.add(d);
                }
            } catch (JsonParseException jpe) {
                Log.e(TAG, "Exception " + jpe);
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }
    }

    public static void getDevicesTypes(Context context) {

        try {
            Map<String, String> postData = new HashMap<>();
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.devices.types",
                            postDataBytes, sSessionKey,
                            String.valueOf(postDataBytesLen));

            Gson gson = new Gson();
            DeviceType devices = gson.fromJson(result, DeviceType.class);

            DeviceLab deviceLab = DeviceLab.get(context);
            List<DeviceType.Types> devicesList = deviceLab.getDeviceTypes();
            devicesList.clear();

            for(DeviceType.Types d : devices.getDevices().getTypes()) {
                d.getTitle();
                d.getId();
                devicesList.add(d);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }
    }

    public static String createDevice(Context context, String devEui, String deviceGateway,
                                    String gatewayId, String insideAddr, String deviceTitle,
                                    String deviceDesc, String deviceType, String keyAp,
                                    String keyNw) {

        String resultCreate = "";

        try {
            Map<String, String> postData = new HashMap<>();
            postData.put("deviceID", devEui);
            postData.put("deviceGateway", deviceGateway);
            postData.put("gatewayID", gatewayId);
            postData.put("inside_addr", insideAddr);
            postData.put("deviceTitle", deviceTitle);
            postData.put("deviceDesc", deviceDesc);
            postData.put("deviceType", deviceType);
            postData.put("keyAp", keyAp);
            postData.put("keyNw", keyNw);
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.devices.create",
                            postDataBytes, sSessionKey,
                            String.valueOf(postDataBytesLen));

            resultCreate = result;
            getDevices(context);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }

        return resultCreate;
    }

    public static String deleteDevice(Context context, String devEui) {

        String resultDelete = "";

        try {
            Map<String, String> postData = new HashMap<>();
            postData.put("deviceId", devEui);
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.devices.remove",
                            postDataBytes, sSessionKey,
                            String.valueOf(postDataBytesLen));

            resultDelete = result;

            getDevices(context);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }
        return resultDelete;
    }

    public static String getMessages(Context context, String devId) {

        String messages = "";

        try {
            Map<String, String> postData = new HashMap<>();
            postData.put("deviceId", devId);
            PostParamBuild postDataBuild = new PostParamBuild();

            byte[] postDataBytes = postDataBuild.postParBuilder(postData).getBytes();
            int postDataBytesLen = postDataBytes.length;

            String result = new SendPost()
                    .sendPostString(TELEMETRIC_URL + "api.devices.messages",
                            postDataBytes, sSessionKey,
                            String.valueOf(postDataBytesLen));

            messages = result;

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch URL: ", ioe);
        }
        return messages;
    }
}
