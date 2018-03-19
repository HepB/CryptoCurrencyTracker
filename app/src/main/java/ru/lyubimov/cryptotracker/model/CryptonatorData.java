package ru.lyubimov.cryptotracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 11.02.2018.
 */

public class CryptonatorData {

    @SerializedName("ticker")
    private Ticker ticker;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("error")
    private String error;

    public class Ticker {
        @SerializedName("markets")
        private List<Market> markets;

        public List<Market> getMarkets() {
            return markets;
        }
        public void setMarkets(List<Market> markets) {
            this.markets = markets;
        }
    }

    public Ticker getTicker() {
        return ticker;
    }
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
