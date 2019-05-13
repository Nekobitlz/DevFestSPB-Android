package com.nekobitlz.devfest_spb.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {

    @GET("data.json")
    Call<ApiData> getData();
}
