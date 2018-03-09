package ru.lyubimov.cryptotracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.lyubimov.cryptotracker.model.CryptoCurrency;
import ru.lyubimov.cryptotracker.model.CryptonatorData;

/**
 * Created by Alex on 08.03.2018.
 */

public interface WebApi {

    @GET("v1/ticker/{cryptoCurrency}")
    Call<List<CryptoCurrency>> getCurrency(@Path("cryptoCurrency") String cryptoCurrency, @Query("limit") int limit, @Query("convert") String currency);

    @GET("api/full/{cryptoPare}")
    Call<CryptonatorData> getMarkets(@Path("cryptoPare") String cryptoPare);
}
