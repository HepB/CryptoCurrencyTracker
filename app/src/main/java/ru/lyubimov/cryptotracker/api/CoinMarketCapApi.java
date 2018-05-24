package ru.lyubimov.cryptotracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.lyubimov.cryptotracker.model.CryptoCurrency;

/**
 * Created by Alex on 10.03.2018.
 */

public interface CoinMarketCapApi {

    @GET("v1/ticker/")
    Call<List<CryptoCurrency>> getCryptoCurrencies(@Query("limit") int limit, @Query("convert") String currency);
}
