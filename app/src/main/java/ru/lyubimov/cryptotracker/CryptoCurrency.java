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
    private int mRank;
    private double mPriceUsd;
    private double mPriceBtc;
    private double mDayVolumeUsd;
    private double mMarketCapUsd;
    private double mAvailableSupply;
    private double mTotalSupply;
    private double mHourPercentChange;
    private double mDayPercentChange;
    private double mWeekPercentChange;
    private long mLastUpdated;

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

    public int getRank() {
        return mRank;
    }
    public void setRank(int rank) {
        this.mRank = rank;
    }

    public double getPriceUsd() {
        return mPriceUsd;
    }
    public void setPriceUsd(double priceUsd) {
        this.mPriceUsd = priceUsd;
    }

    public double getPriceBtc() {
        return mPriceBtc;
    }
    public void setPriceBtc(double priceBtc) {
        this.mPriceBtc = priceBtc;
    }

    public double getDayVolumeUsd() {
        return mDayVolumeUsd;
    }
    public void setDayVolumeUsd(double dayVolumeUsd) {
        this.mDayVolumeUsd = dayVolumeUsd;
    }

    public double getMarketCapUsd() {
        return mMarketCapUsd;
    }
    public void setMarketCapUsd(double marketCapUsd) {
        this.mMarketCapUsd = marketCapUsd;
    }

    public double getAvailableSupply() {
        return mAvailableSupply;
    }
    public void setAvailableSupply(double availableSupply) {
        this.mAvailableSupply = availableSupply;
    }

    public double getTotalSupply() {
        return mTotalSupply;
    }
    public void setTotalSupply(double totalSupply) {
        this.mTotalSupply = totalSupply;
    }

    public double getHourPercentChange() {
        return mHourPercentChange;
    }
    public void setHourPercentChange(double hourPercentChange) {
        this.mHourPercentChange = hourPercentChange;
    }

    public double getDayPercentChange() {
        return mDayPercentChange;
    }
    public void setDayPercentChange(double dayPercentChange) {
        this.mDayPercentChange = dayPercentChange;
    }

    public double getWeekPercentChange() {
        return mWeekPercentChange;
    }
    public void setWeekPercentChange(double weekPercentChange) {
        this.mWeekPercentChange = weekPercentChange;
    }

    public long getLastUpdated() {
        return mLastUpdated;
    }
    public void setLastUpdated(long lastUpdated) {
        this.mLastUpdated = lastUpdated;
    }

    public static CryptoCurrency createCryptoCurrencyByJSON(JSONObject cryptoCurrencyJsonObject) throws JSONException {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();

        cryptoCurrency.setId(cryptoCurrencyJsonObject.getString("mId"));
        cryptoCurrency.setName(cryptoCurrencyJsonObject.getString("mName"));
        cryptoCurrency.setSymbol(cryptoCurrencyJsonObject.getString("mSymbol"));
        cryptoCurrency.setRank(cryptoCurrencyJsonObject.getInt("mRank"));
        cryptoCurrency.setPriceUsd(cryptoCurrencyJsonObject.getDouble("price_usd"));
        cryptoCurrency.setPriceBtc(cryptoCurrencyJsonObject.getDouble("price_btc"));
        cryptoCurrency.setDayVolumeUsd(cryptoCurrencyJsonObject.getDouble("24h_volume_usd"));
        cryptoCurrency.setMarketCapUsd(cryptoCurrencyJsonObject.getDouble("market_cap_usd"));
        cryptoCurrency.setAvailableSupply(cryptoCurrencyJsonObject.getDouble("available_supply"));
        cryptoCurrency.setTotalSupply(cryptoCurrencyJsonObject.getDouble("total_supply"));
        cryptoCurrency.setHourPercentChange(cryptoCurrencyJsonObject.getDouble("percent_change_1h"));
        cryptoCurrency.setDayPercentChange(cryptoCurrencyJsonObject.getDouble("percent_change_24h"));
        cryptoCurrency.setWeekPercentChange(cryptoCurrencyJsonObject.getDouble("percent_change_7d"));
        cryptoCurrency.setLastUpdated(cryptoCurrencyJsonObject.getLong("last_updated"));

        return cryptoCurrency;
    }
}
