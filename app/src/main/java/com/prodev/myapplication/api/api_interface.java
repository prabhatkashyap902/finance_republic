package com.prodev.myapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api_interface {

    String JSONURL = "https://www.alphavantage.co/";
/*query?function=TIME_SERIES_DAILY&symbol=INFY&interval=5min&apikey=J0N70CI83WLLS1BR
    @GET("people")
    Call<String> getString();*/

    @GET("query")
     //i.e https://api.test.com/Search?
    Call<String> getProducts(@Query("function") String function,
                             @Query("symbol") String symbol,@Query("interval") String interval,@Query("apikey") String key);



    @GET(".")
    Call<String> getString();
}
