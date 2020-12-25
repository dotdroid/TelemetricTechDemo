package ru.dotdroid.telemetrictechdemo.json;

import com.google.gson.annotations.SerializedName;

public class Device {
    @SerializedName("company_id")
    private String mCompanyId;
    @SerializedName("id")
    private String mId;
    @SerializedName("deviceID")
    private String mDeviceEui;
    @SerializedName("port_addr")
    private String mPortAddr;
    @SerializedName("gatewayID")
    private String mGatewayId;
    @SerializedName("inside_addr")
    private String mInsideAddr;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("device_type_id")
    private String mDevice_Type_Id;
    @SerializedName("desc")
    private String mDesc;
    @SerializedName("creator_id")
    private String mCreatorId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("last_active")
    private String mLastActive;
    @SerializedName("last_message")
    private String mLastMessage;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted")
    private boolean mDeleted;
    @SerializedName("keyAp")
    private String mKeyAp;
    @SerializedName("keyNw")
    private String mKeyNw;
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
    @SerializedName("calibration_json")
    private String mCalibrationJson;
    @SerializedName("allow_trigger")
    private String mAllowTrigger;
    @SerializedName("deviceTypeID")
    private String mDeviceTypeId;
    @SerializedName("type_title")
    private String mTypeTitle;
    @SerializedName("photo_url")
    private String mPhotoUrl;
    @SerializedName("can_gateway")
    private String mCanGateway;
    @SerializedName("device_group_id")
    private String mDeviceGroupId;
    @SerializedName("icon_group")
    private String mIconGroup;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDeviceEui() {
        return mDeviceEui;
    }

    public void setDeviceEui(String deviceEui) {
        mDeviceEui = deviceEui;
    }

    public String getPortAddr() {
        return mPortAddr;
    }

    public void setPortAddr(String portAddr) {
        mPortAddr = portAddr;
    }

    public String getGatewayId() {
        return mGatewayId;
    }

    public void setGatewayId(String gatewayId) {
        mGatewayId = gatewayId;
    }

    public String getInsideAddr() {
        return mInsideAddr;
    }

    public void setInsideAddr(String insideAddr) {
        mInsideAddr = insideAddr;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDevice_Type_Id() {
        return mDevice_Type_Id;
    }

    public void setDevice_Type_Id(String device_Type_Id) {
        mDevice_Type_Id = device_Type_Id;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getCreatorId() {
        return mCreatorId;
    }

    public void setCreatorId(String creatorId) {
        mCreatorId = creatorId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getLastActive() {
        return mLastActive;
    }

    public void setLastActive(String lastActive) {
        mLastActive = lastActive;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean deleted) {
        mDeleted = deleted;
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

    public String getCalibrationJson() {
        return mCalibrationJson;
    }

    public void setCalibrationJson(String calibrationJson) {
        mCalibrationJson = calibrationJson;
    }

    public String getAllowTrigger() {
        return mAllowTrigger;
    }

    public void setAllowTrigger(String allowTrigger) {
        mAllowTrigger = allowTrigger;
    }

    public String getDeviceTypeId() {
        return mDeviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        mDeviceTypeId = deviceTypeId;
    }

    public String getTypeTitle() {
        return mTypeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        mTypeTitle = typeTitle;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getCanGateway() {
        return mCanGateway;
    }

    public void setCanGateway(String canGateway) {
        mCanGateway = canGateway;
    }

    public String getDeviceGroupId() {
        return mDeviceGroupId;
    }

    public void setDeviceGroupId(String deviceGroupId) {
        mDeviceGroupId = deviceGroupId;
    }

    public String getIconGroup() {
        return mIconGroup;
    }

    public void setIconGroup(String iconGroup) {
        mIconGroup = iconGroup;
    }
}
