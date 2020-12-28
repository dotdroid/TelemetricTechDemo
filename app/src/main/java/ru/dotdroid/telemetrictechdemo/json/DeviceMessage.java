package ru.dotdroid.telemetrictechdemo.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceMessage extends Device {
    @SerializedName("messages")
    private List<Messages> mMessages;
    @SerializedName("timeZone")
    private String mTimeZone;
    @SerializedName("debug")
    private Debug mDebug;

    public class Messages {

        //generic
        @SerializedName("loRaSNR")
        private double mLoRaSnr;
        @SerializedName("datetime")
        private long mDateTime;
        @SerializedName("loRaRSSI")
        private int mLoRaRssi;
        @SerializedName("open_case")
        private int mOpenCase;
        @SerializedName("battery_level")
        private int mBatteryLevel;
        @SerializedName("batteryLevel")
        private int mBatteryLevel2;
        @SerializedName("loRaGatewayID")
        private String mLoRaGatewayId;
        @SerializedName("device_datetime")
        private String mDeviceDatetime;
        @SerializedName("record_datetime")
        private String mRecordDatetime;
        @SerializedName("device_timestamp")
        private long mDeviceTimestamp;
        @SerializedName("deviceTimestamp")
        private long mDeviceTimestamp2;
        @SerializedName("long_magnet_flag")
        private int mLongMagnetFlag;
        @SerializedName("record_timestamp")
        private long mRecordTimestamp;
        @SerializedName("recordTimestamp")
        private long mRecordTimestamp2;
        @SerializedName("impact_magnet_flag")
        private int mImpactMagnetFlag;
        @SerializedName("signMagnetFlag")
        private int mSignMagnetFlag;
        @SerializedName("signOpenCase")
        private int mSignOpenCase;
        @SerializedName("signLinkButton")
        private int mSignLinkButton;

        //water
        @SerializedName("leakDetectionFlag")
        private int mLeakDetectionFlag;
        @SerializedName("direct_flow_liter_counter")
        private int mDirectFlowLiterCounter;
        @SerializedName("counter_reverse_flow_liters")
        private int mCounterReverseFlowLiters;
        @SerializedName("optical_sensor_illumination")
        private int mOpticalSensorIllumination;
        @SerializedName("direct_flow_liter_counter_spread")
        private double mDirectFlowLiterCounterSpread;
        @SerializedName("start_direct_flow_liter_counter")
        private double mStartDirectFlowLiterCounter;
        @SerializedName("end_direct_flow_liter_counter")
        private double mEndDirectFlowLiterCounter;
        @SerializedName("delta_direct_flow_liter_counter")
        private double mDeltaDirectFlowLiterCounter;

        //inertia
        @SerializedName("signAlarm")
        private int mSignAlarm;
        @SerializedName("packageType")
        private String mPackageType;
        @SerializedName("magnetometerAngle")
        private float mMagnetometerAngle;
        @SerializedName("magnetometerAxisX")
        private int mMagnetometerAxisX;
        @SerializedName("magnetometerAxisY")
        private int mMagnetometerAxisY;
        @SerializedName("magnetometerAxisZ")
        private int mMagnetometerAxisZ;
        @SerializedName("accelerometerAngle")
        private float mAccelerometerAngle;
        @SerializedName("accelerometerAxisX")
        private int mAccelerometerAxisX;
        @SerializedName("accelerometerAxisY")
        private int mAccelerometerAxisY;
        @SerializedName("accelerometerAxisZ")
        private int mAccelerometerAxisZ;

        public double getLoRaSnr() {
            return mLoRaSnr;
        }

        public long getDateTime() {
            return mDateTime;
        }

        public int getLoRaRssi() {
            return mLoRaRssi;
        }

        public int getOpenCase() {
            return mOpenCase;
        }

        public int getBatteryLevel() {
            return mBatteryLevel;
        }

        public int getBatteryLevel2() {
            return mBatteryLevel2;
        }

        public String getLoRaGatewayId() {
            return mLoRaGatewayId;
        }

        public String getDeviceDatetime() {
            return mDeviceDatetime;
        }

        public String getRecordDatetime() {
            return mRecordDatetime;
        }

        public long getDeviceTimestamp() {
            return mDeviceTimestamp;
        }

        public long getDeviceTimestamp2() {
            return mDeviceTimestamp2;
        }

        public int getLongMagnetFlag() {
            return mLongMagnetFlag;
        }

        public long getRecordTimestamp() {
            return mRecordTimestamp;
        }

        public long getRecordTimestamp2() {
            return mRecordTimestamp2;
        }

        public int getImpactMagnetFlag() {
            return mImpactMagnetFlag;
        }

        public int getSignMagnetFlag() {
            return mSignMagnetFlag;
        }

        public int getSignOpenCase() {
            return mSignOpenCase;
        }

        public int getSignLinkButton() {
            return mSignLinkButton;
        }

        public int getLeakDetectionFlag() {
            return mLeakDetectionFlag;
        }

        public int getDirectFlowLiterCounter() {
            return mDirectFlowLiterCounter;
        }

        public int getCounterReverseFlowLiters() {
            return mCounterReverseFlowLiters;
        }

        public int getOpticalSensorIllumination() {
            return mOpticalSensorIllumination;
        }

        public double getDirectFlowLiterCounterSpread() {
            return mDirectFlowLiterCounterSpread;
        }

        public double getStartDirectFlowLiterCounter() {
            return mStartDirectFlowLiterCounter;
        }

        public double getEndDirectFlowLiterCounter() {
            return mEndDirectFlowLiterCounter;
        }

        public double getDeltaDirectFlowLiterCounter() {
            return mDeltaDirectFlowLiterCounter;
        }

        public int getSignAlarm() {
            return mSignAlarm;
        }

        public String getPackageType() {
            return mPackageType;
        }

        public float getMagnetometerAngle() {
            return mMagnetometerAngle;
        }

        public int getMagnetometerAxisX() {
            return mMagnetometerAxisX;
        }

        public int getMagnetometerAxisY() {
            return mMagnetometerAxisY;
        }

        public int getMagnetometerAxisZ() {
            return mMagnetometerAxisZ;
        }

        public float getAccelerometerAngle() {
            return mAccelerometerAngle;
        }

        public int getAccelerometerAxisX() {
            return mAccelerometerAxisX;
        }

        public int getAccelerometerAxisY() {
            return mAccelerometerAxisY;
        }

        public int getAccelerometerAxisZ() {
            return mAccelerometerAxisZ;
        }
    }

    public class Debug {
        @SerializedName("code")
        private String mCode;
        @SerializedName("answer")
        private List<Answer> mAnswer;

        public String getCode() {
            return mCode;
        }

        public List<DeviceMessage.Answer> getAnswer() {
            return mAnswer;
        }
    }

    public class Answer {
        @SerializedName("datetime")
        private String mDatetime;
        @SerializedName("message")
        private String mMessage;

        public String getDatetime() {
            return mDatetime;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    public List<DeviceMessage.Messages> getMessages() {
        return mMessages;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public DeviceMessage.Debug getDebug() {
        return mDebug;
    }
}
