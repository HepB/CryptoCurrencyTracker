package ru.lyubimov.cryptotracker;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.lyubimov.cryptotracker.model.CryptoCurrency;
import ru.lyubimov.cryptotracker.model.Currency;

/**
 * Created by Alex on 15.01.2018.
 */

public class CoinMarketCapFetcher extends WebDataFetcher{
    private static final String TAG = "CoinMarketCapFetcher";

    private static final Uri ENDPOINT = Uri.parse("https://api.coinmarketcap.com/v1/ticker/");
    private static final String LIMIT = "limit";
    private static final String CONVERT = "convert";

    public CoinMarketCapFetcher(Resources resources) {
        super(resources);
    }

    public List<CryptoCurrency> fetchCryptoCurrencies() throws IOException {
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

    private List<CryptoCurrency> downloadCryptoCurrencies(String url) throws IOException {
        List<CryptoCurrency> items = new ArrayList<>();
        String jsonString = getUrlString(url);
        parseItems(items, jsonString);
        return items;
    }

    private void parseItems(List<CryptoCurrency> items, String jsonSting) {
        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<CryptoCurrency>>(){}.getType();
            List<CryptoCurrency> cryptoCurrencies = gson.fromJson(jsonSting, collectionType);
            items.addAll(cryptoCurrencies);
        } catch (Exception ex) {
            throw new RuntimeException(getResources().getString(R.string.parse_crypto_cur_exception));
        }
    }
}
