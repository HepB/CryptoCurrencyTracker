package ru.lyubimov.cryptotracker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lyubimov-AA on 16.01.2018.
 */

public class CryptoCurrency {

    private static final String NULL = "null";

    private String mId;
    private String mName;
    private String mSymbol;
    private String mRank;
    private String mPriceCur;
    private String mPriceBtc;
    private String mDayVolumeCur;
    private String mMarketCapCur;
    private String mAvailableSupply;
    private String mTotalSupply;
    private String mHourPercentChange;
    private String mDayPercentChange;
    private String mWeekPercentChange;
    private String mLastUpdated;

    public String getId() {
        return mId;
    }
    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public String getSymbol() {
        return mSymbol;
    }
    public void setSymbol(String symbol) {
        this.mSymbol = symbol;
    }

    public String getRank() {
        return mRank;
    }
    public void setRank(String rank) {
        this.mRank = rank;
    }

    public String getPriceCur() {
        return mPriceCur;
    }
    public void setPriceCur(String priceCur) {
        this.mPriceCur = priceCur;
    }

    public String getPriceBtc() {
        return mPriceBtc;
    }
    public void setPriceBtc(String priceBtc) {
        this.mPriceBtc = priceBtc;
    }

    public String getDayVolumeCur() {
        return mDayVolumeCur;
    }
    public void setDayVolumeCur(String dayVolumeCur) {
        this.mDayVolumeCur = dayVolumeCur;
    }

    public String getMarketCapCur() {
        return mMarketCapCur;
    }
    public void setMarketCapCur(String marketCapCur) {
        this.mMarketCapCur = marketCapCur;
    }

    public String getAvailableSupply() {
        return mAvailableSupply;
    }
    public void setAvailableSupply(String availableSupply) {
        this.mAvailableSupply = availableSupply;
    }

    public String getTotalSupply() {
        return mTotalSupply;
    }
    public void setTotalSupply(String totalSupply) {
        this.mTotalSupply = totalSupply;
    }

    public String getHourPercentChange() {
        return mHourPercentChange;
    }
    public void setHourPercentChange(String hourPercentChange) {
        this.mHourPercentChange = hourPercentChange;
    }

    public String getDayPercentChange() {
        return mDayPercentChange;
    }
    public void setDayPercentChange(String dayPercentChange) {
        this.mDayPercentChange = dayPercentChange;
    }

    public String getWeekPercentChange() {
        return mWeekPercentChange;
    }
    public void setWeekPercentChange(String weekPercentChange) {
        this.mWeekPercentChange = weekPercentChange;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.mLastUpdated = lastUpdated;
    }

    //TODO не забыть переделать на различные запросы, с возможностью выбора конкретной валюты помимо USD
    public static CryptoCurrency createCryptoCurrencyByDefaultJSON(JSONObject cryptoCurrencyJsonObject) throws JSONException {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();

        String id = cryptoCurrencyJsonObject.getString("id");
        cryptoCurrency.setId(id.equalsIgnoreCase(NULL) ? null : id);

        String name = cryptoCurrencyJsonObject.getString("name");
        cryptoCurrency.setName(name.equalsIgnoreCase(NULL) ? null : name);

        String symbol = cryptoCurrencyJsonObject.getString("symbol");
        cryptoCurrency.setSymbol(symbol.equalsIgnoreCase(NULL) ? null : symbol);

        String rank = cryptoCurrencyJsonObject.getString("rank");
        cryptoCurrency.setRank(rank.equalsIgnoreCase(NULL) ? null : rank);

        String priceUsd = cryptoCurrencyJsonObject.getString("price_usd");
        cryptoCurrency.setPriceCur(priceUsd.equalsIgnoreCase(NULL) ? null : priceUsd);

        String priceBtc = cryptoCurrencyJsonObject.getString("price_btc");
        cryptoCurrency.setPriceBtc(priceBtc.equalsIgnoreCase(NULL) ? null : priceBtc);

        String dayVolumeUsd = cryptoCurrencyJsonObject.getString("24h_volume_usd");
        cryptoCurrency.setDayVolumeCur(dayVolumeUsd.equalsIgnoreCase(NULL) ? null : dayVolumeUsd);

        String marketCapCur = cryptoCurrencyJsonObject.getString("market_cap_usd");
        cryptoCurrency.setMarketCapCur(marketCapCur.equalsIgnoreCase(NULL) ? null : marketCapCur);

        String availableSupply = cryptoCurrencyJsonObject.getString("available_supply");
        cryptoCurrency.setAvailableSupply(availableSupply.equalsIgnoreCase(NULL) ? null : availableSupply);

        String totalSupply = cryptoCurrencyJsonObject.getString("total_supply");
        cryptoCurrency.setTotalSupply(totalSupply.equalsIgnoreCase(NULL) ? null : totalSupply);

        String hourPerChange = cryptoCurrencyJsonObject.getString("percent_change_1h");
        cryptoCurrency.setHourPercentChange(hourPerChange.equalsIgnoreCase(NULL) ? null : hourPerChange);

        String dayPercentChange = cryptoCurrencyJsonObject.getString("percent_change_24h");
        cryptoCurrency.setDayPercentChange(dayPercentChange.equalsIgnoreCase(NULL) ? null : dayPercentChange);

        String weekPerChange = cryptoCurrencyJsonObject.getString("percent_change_7d");
        cryptoCurrency.setWeekPercentChange(weekPerChange.equalsIgnoreCase(NULL) ? null : weekPerChange);

        String lastUpdated = cryptoCurrencyJsonObject.getString("last_updated");
        cryptoCurrency.setLastUpdated(lastUpdated.equalsIgnoreCase(NULL) ? null : lastUpdated);

        return cryptoCurrency;
    }
}
