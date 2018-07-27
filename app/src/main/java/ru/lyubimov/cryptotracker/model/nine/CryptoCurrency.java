package ru.lyubimov.cryptotracker.model.nine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ru.lyubimov.cryptotracker.utils.gson.QuesToNullAdapter;

public class CryptoCurrency implements Serializable{
    @Expose
    @SerializedName("last_updated")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mLastUpdated;
    @Expose
    @SerializedName("total_supply")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mTotalSupply;
    @Expose
    @SerializedName("available_supply")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mAvailableSupply;
    @Expose
    @SerializedName("ranking_market_cap")
    private String mRankingMarketCap;
    @Expose
    @SerializedName("ranking_volume")
    private String mRankingVolume;
    @Expose
    @SerializedName("percent_change_7d")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mPercentChange7d;
    @Expose
    @SerializedName("percent_change_btc_24h")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mPercentChangeBtc24h;
    @Expose
    @SerializedName("percent_change_24h")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mPercentChange24h;
    @Expose
    @SerializedName("percent_change_1h")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mPercentChange1h;
    @Expose
    @SerializedName("market_cap_usd")
    @JsonAdapter(QuesToNullAdapter.class)
    private String mMarketCapUsd;
    @Expose
    @SerializedName("volume_usd_24h")
    private String mVolumeUsd24h;
    @Expose
    @SerializedName("price_btc")
    private String mPriceBtc;
    @Expose
    @SerializedName("price_usd")
    private String mPriceUsd;
    @Expose
    @SerializedName("symbol")
    private String symbol;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private String id;

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public String getmTotalSupply() {
        return mTotalSupply;
    }

    public String getAvailableSupply() {
        return mAvailableSupply;
    }

    public String getRankingMarketCap() {
        return mRankingMarketCap;
    }

    public String getRankingVolume() {
        return mRankingVolume;
    }

    public String getPercentChange7d() {
        return mPercentChange7d;
    }

    public String getPercentChangeBtc24h() {
        return mPercentChangeBtc24h;
    }

    public String getPercentChange24h() {
        return mPercentChange24h;
    }

    public String getPercentChange1h() {
        return mPercentChange1h;
    }

    public String getMarketCapUsd() {
        return mMarketCapUsd;
    }

    public String getVolumeUsd24h() {
        return mVolumeUsd24h;
    }

    public String getPriceBtc() {
        return mPriceBtc;
    }

    public String getPriceUsd() {
        return mPriceUsd;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
