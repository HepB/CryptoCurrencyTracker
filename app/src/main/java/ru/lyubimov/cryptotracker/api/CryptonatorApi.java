package ru.lyubimov.cryptotracker.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.lyubimov.cryptotracker.model.CryptonatorData;

/**
 * Created by Alex on 10.03.2018.
 */

public interface CryptonatorApi {

    @GET("api/full/{cryptoPare}")
    Call<CryptonatorData> getMarkets(@Path("cryptoPare") String cryptoPare);
}
