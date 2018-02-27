package ru.lyubimov.cryptotracker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 11.02.2018.
 */

public class Market {
    @SerializedName("market")
    private String marketName;
    @SerializedName("price")
    private String price;
    @SerializedName("volume")
    private String volume;

    public void setMarketName(String name) {
        this.marketName = name;
    }
    public String getMarketName() {
        return marketName;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
}
