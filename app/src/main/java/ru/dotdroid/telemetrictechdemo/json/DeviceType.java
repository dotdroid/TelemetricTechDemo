package ru.dotdroid.telemetrictechdemo.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceType {
    @SerializedName("devices")
    private Types mDevices;

    public Types getDevices() {
        return mDevices;
    }

    public void setDevices(Types devices) {
        mDevices = devices;
    }

    public class Types {
        @SerializedName("types")
        private List<Types> mTypes;
        @SerializedName("id")
        private String mId;
        @SerializedName("deviceTypeID")
        private String mDeviceTypeID;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("keyAp")
        private String mKeyAp;
        @SerializedName("keyNw")
        private String mKeyNw;
        @SerializedName("group_id")
        private String mGroupId;
        @SerializedName("amount_inputs")
        private String mAmountInputs;
        @SerializedName("amount_outputs")
        private String mAmountOutputs;
        @SerializedName("_232")
        private String m232;
        @SerializedName("_485")
        private String m485;
        @SerializedName("report_period_update")
        private String mReportPeriodUpdate;
        @SerializedName("report_online_settings")
        private String mReportOnlineSettings;
        @SerializedName("allow_trigger")
        private String mAllowTrigger;
        @SerializedName("can_gateway")
        private String mCanGateway;
        @SerializedName("photo_url")
        private String mPhotoUrl;
        @SerializedName("calibration_json")
        private String mCalibrationJson;
        @SerializedName("active")
        private String mActive;

        @Override
        public String toString() {
            return mTitle;
        }

        public List<Types> getTypes() {
            return mTypes;
        }

        public void setTypes(List<Types> types) {
            mTypes = types;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getDeviceTypeID() {
            return mDeviceTypeID;
        }

        public void setDeviceTypeID(String deviceTypeID) {
            mDeviceTypeID = deviceTypeID;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getKeyAp() {
            return mKeyAp;
        }

        public void setKeyAp(String keyAp) {
            mKeyAp = keyAp;
        }

        public String getKeyNw() {
            return mKeyNw;
        }

        public void setKeyNw(String keyNw) {
            mKeyNw = keyNw;
        }

        public String getGroupId() {
            return mGroupId;
        }

        public void setGroupId(String groupId) {
            mGroupId = groupId;
        }

        public String getAmountInputs() {
            return mAmountInputs;
        }

        public void setAmountInputs(String amountInputs) {
            mAmountInputs = amountInputs;
        }

        public String getAmountOutputs() {
            return mAmountOutputs;
        }

        public void setAmountOutputs(String amountOutputs) {
            mAmountOutputs = amountOutputs;
        }

        public String getM232() {
            return m232;
        }

        public void setM232(String m232) {
            this.m232 = m232;
        }

        public String getM485() {
            return m485;
        }

        public void setM485(String m485) {
            this.m485 = m485;
        }

        public String getReportPeriodUpdate() {
            return mReportPeriodUpdate;
        }

        public void setReportPeriodUpdate(String reportPeriodUpdate) {
            mReportPeriodUpdate = reportPeriodUpdate;
        }

        public String getReportOnlineSettings() {
            return mReportOnlineSettings;
        }

        public void setReportOnlineSettings(String reportOnlineSettings) {
            mReportOnlineSettings = reportOnlineSettings;
        }

        public String getAllowTrigger() {
            return mAllowTrigger;
        }

        public void setAllowTrigger(String allowTrigger) {
            mAllowTrigger = allowTrigger;
        }

        public String getCanGateway() {
            return mCanGateway;
        }

        public void setCanGateway(String canGateway) {
            mCanGateway = canGateway;
        }

        public String getPhotoUrl() {
            return mPhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            mPhotoUrl = photoUrl;
        }

        public String getCalibrationJson() {
            return mCalibrationJson;
        }

        public void setCalibrationJson(String calibrationJson) {
            mCalibrationJson = calibrationJson;
        }

        public String getActive() {
            return mActive;
        }

        public void setActive(String active) {
            mActive = active;
        }
    }
}
