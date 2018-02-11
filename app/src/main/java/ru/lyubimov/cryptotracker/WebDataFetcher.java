package ru.lyubimov.cryptotracker;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 11.02.2018.
 */

public abstract class WebDataFetcher {
    private static final String TAG = "WebDataFetcher";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                IOException ioe = new IOException(connection.getResponseMessage() + ": with " + urlSpec);
                Log.e(TAG, ioe.getMessage());
                throw ioe;
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }
}
