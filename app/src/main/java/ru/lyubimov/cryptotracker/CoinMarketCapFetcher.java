package ru.lyubimov.cryptotracker;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 15.01.2018.
 */

public class CoinMarketCapFetcher {
    private static final String TAG = "CoinMarketCapFetcher";

    private static final Uri ENDPOINT = Uri.parse("https://api.coinmarketcap.com/v1/ticker/");
    private static final String LIMIT = "limit";
    private static final String CONVERT = "convert";

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

    public List<CryptoCurrency> fetchCryptoCurrencies() {
        String url = buildUrl();
        return downloadCryptoCurrencies(url);
    }

    String buildUrl() {
        return buildUrl(0, null, null);
    }

    String buildUrl(int limit, @Nullable String cryptoCurrency, @Nullable Currency currency) {
        Uri.Builder builder;
        if (cryptoCurrency != null && currency != null) {
            builder = ENDPOINT.buildUpon()
                    .appendEncodedPath(cryptoCurrency)
                    .appendQueryParameter(CONVERT, currency.toString())
                    .appendQueryParameter(LIMIT, Integer.toString(limit));
        } else if (cryptoCurrency != null) {
            builder = ENDPOINT.buildUpon()
                    .appendEncodedPath(cryptoCurrency)
                    .appendQueryParameter(LIMIT, Integer.toString(limit));
        } else if (currency != null) {
            builder = ENDPOINT.buildUpon()
                    .appendQueryParameter(CONVERT, currency.toString())
                    .appendQueryParameter(LIMIT, Integer.toString(limit));
        } else {
            builder = ENDPOINT.buildUpon()
                    .appendQueryParameter(LIMIT, Integer.toString(limit));
        }
        return builder.build().toString();
    }

    String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    private List<CryptoCurrency> downloadCryptoCurrencies(String url) {
        List<CryptoCurrency> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            JSONArray jsonBody = new JSONArray(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    private void parseItems(List<CryptoCurrency> items, JSONArray jsonArray) throws IOException, JSONException{

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject cryptoCurrencyJsonObject = jsonArray.getJSONObject(i);
            CryptoCurrency cryptoCurrency = CryptoCurrency.createCryptoCurrencyByJSON(cryptoCurrencyJsonObject);
            items.add(cryptoCurrency);
        }
    }

}
