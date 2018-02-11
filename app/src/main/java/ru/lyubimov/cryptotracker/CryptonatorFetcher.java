package ru.lyubimov.cryptotracker;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11.02.2018.
 */

public class CryptonatorFetcher extends WebDataFetcher{
    private static final String TAG = "CryptonatorFetcher";

    private static final Uri ENDPOINT = Uri.parse("https://api.cryptonator.com/api/full/");

    String buildUrl(String targetCurrSymbol, String costCurrencyType) {
        Uri.Builder builder;
        String urlTail = targetCurrSymbol + "-" + costCurrencyType;
        builder = ENDPOINT.buildUpon().appendPath(urlTail);

        return builder.build().toString();
    }

    ArrayList<Market> downloadCryptoCurrencies(String targetCurrSymbol, String costCurrencyType) {
        ArrayList<Market> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(targetCurrSymbol, costCurrencyType);
            parseItems(items, jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return items;
    }

    String getUrlString(String targetCurrSymbol, String costCurrencyType) throws IOException {
        String completeUrl= buildUrl(targetCurrSymbol, costCurrencyType);
        return new String(getUrlBytes(completeUrl));
    }

    private void parseItems(List<Market> items, String jsonSting) {

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonSting);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if(jsonObject == null) return;
        JsonObject ticker = jsonObject.getAsJsonObject("ticker");

        if(ticker == null) return;
        JsonArray jsonArray = ticker.get("markets").getAsJsonArray();

        if (jsonArray == null) return;
        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<Market>>(){}.getType();
        List<Market> markets = gson.fromJson(jsonArray, collectionType);
        items.addAll(markets);
    }
}
