package ru.dotdroid.telemetrictechdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class TelemetricTechMethods {
    private static final String AUTH_PARAM = "authKey";

    public byte[] sendLoginBytes(String urlSpec, String authKey) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        Map<String, String> data = new HashMap<>();
        data.put(AUTH_PARAM, authKey);
        byte[] POSTData = POSTParBuilder(data).getBytes();

        connection.setDoOutput( true );
        connection.setInstanceFollowRedirects( false );
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Length", String.valueOf(POSTData.length));
        connection.getOutputStream().write(POSTData);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String sendLoginString(String urlSpec, String authKey) throws IOException {
        return new String(sendLoginBytes(urlSpec, authKey));
    }

    public byte[] searchByDevEUIBytes(String urlSpec, String devEUI) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        Map<String, String> data = new HashMap<>();
        data.put("search", devEUI);

        byte[] POSTData = POSTParBuilder(data).getBytes();

        connection.setDoOutput( true );
        connection.setInstanceFollowRedirects( false );
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Cookie", "TELEMETRIC="+ LoginScreen.sessionKey);
        connection.setRequestProperty("Content-Length", String.valueOf(POSTData.length));
        connection.getOutputStream().write(POSTData);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String searchByDevEUIString(String urlSpec, String devEUI) throws IOException {
        return new String(searchByDevEUIBytes(urlSpec, devEUI));
    }

    private static String POSTParBuilder (Map<String, String> data) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, String> entry : data.entrySet()) {
            if(builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), String.valueOf(StandardCharsets.UTF_8)));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), String.valueOf(StandardCharsets.UTF_8)));
        }
        return builder.toString();
    }

}
