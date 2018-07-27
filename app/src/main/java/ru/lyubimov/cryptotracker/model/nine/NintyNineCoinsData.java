package ru.lyubimov.cryptotracker.model.nine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NintyNineCoinsData {

    @Expose
    @SerializedName("result")
    private List<CryptoCurrency> result;
    @Expose
    @SerializedName("status")
    private String status;

    public List<CryptoCurrency> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }
}
