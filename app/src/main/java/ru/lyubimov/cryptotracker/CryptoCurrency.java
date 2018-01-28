package ru.lyubimov.cryptotracker;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lyubimov-AA on 16.01.2018.
 */

public class CryptoCurrency implements Serializable{

    private static final String NULL = "null";

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("symbol")
    private String mSymbol;
    @SerializedName("rank")
    private String mRank;
    @SerializedName("price_usd")
    private String mPriceCur;
    @SerializedName("price_btc")
    private String mPriceBtc;
    @SerializedName("24h_volume_usd")
    private String mDayVolumeCur;
    @SerializedName("market_cap_usd")
    private String mMarketCapCur;
    @SerializedName("available_supply")
    private String mAvailableSupply;
    @SerializedName("total_supply")
    private String mTotalSupply;
    @SerializedName("max_supply")
    private String mMaxSupply;
    @SerializedName("percent_change_1h")
    private String mHourPercentChange;
    @SerializedName("percent_change_24h")
    private String mDayPercentChange;
    @SerializedName("percent_change_7d")
    private String mWeekPercentChange;
    @SerializedName("last_updated")
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

    public String getMaxSupply() {
        return mMaxSupply;
    }
    public void setMaxSupply(String maxSupply) {
        mMaxSupply = maxSupply;
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
}
