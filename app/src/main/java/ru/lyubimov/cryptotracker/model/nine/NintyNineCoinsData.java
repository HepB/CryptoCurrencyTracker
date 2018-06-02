package ru.lyubimov.cryptotracker.model.nine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NintyNineCoinsData {

    @Expose
    @SerializedName("result")
    private List<CCurrency> result;
    @Expose
    @SerializedName("status")
    private String status;

    public List<CCurrency> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }
}
