package ru.lyubimov.cryptotracker.api;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.lyubimov.cryptotracker.model.nine.NintyNineCoinsData;

public interface NintyNineCoinsApi {

    @GET("/v1/marketcap/ticker")
    Call<NintyNineCoinsData> getCryptoCurrencies();
}
