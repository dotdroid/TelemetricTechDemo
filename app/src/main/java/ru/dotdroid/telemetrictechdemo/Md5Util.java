package ru.dotdroid.telemetrictechdemo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = 0 + md5Hex;
        }

        return md5Hex;
    }
}