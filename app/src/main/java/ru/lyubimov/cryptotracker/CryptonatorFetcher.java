package ru.lyubimov.cryptotracker;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.lyubimov.cryptotracker.model.Market;

/**
 * Created by Alex on 11.02.2018.
 */

public class CryptonatorFetcher extends WebDataFetcher {
    private static final String TAG = "CryptonatorFetcher";

    private static final Uri ENDPOINT = Uri.parse("https://api.cryptonator.com/api/full/");

    CryptonatorFetcher(Resources resources) {
        super(resources);
    }


    String buildUrl(String targetCurrSymbol, String costCurrencyType) {
        Uri.Builder builder;
        String urlTail = targetCurrSymbol + "-" + costCurrencyType;
        builder = ENDPOINT.buildUpon().appendPath(urlTail);

        return builder.build().toString();
    }

    ArrayList<Market> downloadCryptoCurrencies(String targetCurrSymbol, String costCurrencyType) throws IOException {
        ArrayList<Market> items = new ArrayList<>();
        String jsonString = getUrlString(targetCurrSymbol, costCurrencyType);
        parseItems(items, jsonString);
        return items;
    }

    private String getUrlString(String targetCurrSymbol, String costCurrencyType) throws IOException {
        String completeUrl = buildUrl(targetCurrSymbol, costCurrencyType);
        return new String(getUrlBytes(completeUrl));
    }

    private void parseItems(List<Market> items, String jsonSting) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(jsonSting);

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject ticker = jsonObject.getAsJsonObject("ticker");
            JsonArray jsonArray = ticker.get("markets").getAsJsonArray();

            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Market>>() {}.getType();
            List<Market> markets = gson.fromJson(jsonArray, collectionType);
            items.addAll(markets);

        } catch (Exception e) {
            Log.e(TAG, "Failed to parse items", e);
            throw new RuntimeException(getResources().getString(R.string.parse_markets_exception));
        }
    }
}
