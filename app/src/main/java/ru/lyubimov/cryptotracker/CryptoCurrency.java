package ru.lyubimov.cryptotracker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lyubimov-AA on 16.01.2018.
 */

public class CryptoCurrency {

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
    public static CryptoCurrency createCryptoCurrencyByJSON(JSONObject cryptoCurrencyJsonObject) throws JSONException {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();

        cryptoCurrency.setId(cryptoCurrencyJsonObject.getString("id"));
        cryptoCurrency.setName(cryptoCurrencyJsonObject.getString("name"));
        cryptoCurrency.setSymbol(cryptoCurrencyJsonObject.getString("symbol"));
        cryptoCurrency.setRank(cryptoCurrencyJsonObject.getString("rank"));
        cryptoCurrency.setPriceCur(cryptoCurrencyJsonObject.getString("price_usd"));
        cryptoCurrency.setPriceBtc(cryptoCurrencyJsonObject.getString("price_btc"));
        cryptoCurrency.setDayVolumeCur(cryptoCurrencyJsonObject.getString("24h_volume_usd"));
        cryptoCurrency.setMarketCapCur(cryptoCurrencyJsonObject.getString("market_cap_usd"));
        cryptoCurrency.setAvailableSupply(cryptoCurrencyJsonObject.getString("available_supply"));
        cryptoCurrency.setTotalSupply(cryptoCurrencyJsonObject.getString("total_supply"));
        cryptoCurrency.setHourPercentChange(cryptoCurrencyJsonObject.getString("percent_change_1h"));
        cryptoCurrency.setDayPercentChange(cryptoCurrencyJsonObject.getString("percent_change_24h"));
        cryptoCurrency.setWeekPercentChange(cryptoCurrencyJsonObject.getString("percent_change_7d"));
        cryptoCurrency.setLastUpdated(cryptoCurrencyJsonObject.getString("last_updated"));

        return cryptoCurrency;
    }
}
