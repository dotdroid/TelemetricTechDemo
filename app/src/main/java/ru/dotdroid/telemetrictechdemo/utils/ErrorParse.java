package ru.dotdroid.telemetrictechdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ErrorParse {

    public static String parse(int error) {
        switch (error) {
            case 0: return "Unknown Error";
            case 99: return "Invalid request";
            case 100: return "Wrong email or password";
            case 105: return "User is banned";
            case 106: return "The user is only allowed to use the API";
            case 107: return "The user is not permitted to use the API";
            case 303: return "The user with this ID does not exist";
            case 305: return "The user token not md5 string";
            case 306: return "Invalid gateway";
            case 307: return "The device with this ID already exists";
            case 309: return "A device with this ID does not exist or no access to this device";
            case 310: return "Unknown type (ID) device";
            case 311: return "The device has the indicator of removal and is in the basket";
            case 312: return "The device does not have a deletion flag";
            case 500: return "You do not have permission to perform this operation";
            case 700: return "Sorry, an error occurred on the server, our team is already dealing with this problem";
            case 1000: return "Restriction of the license";
            case 6000: return "No connection to data server";
        }
        return null;
    }

    public static void errorToast(Context context, int error) {
        String errorText = ErrorParse.parse(error);
        Toast.makeText(context, "Error " + error + " " + errorText, Toast.LENGTH_SHORT).show();
    }
}
