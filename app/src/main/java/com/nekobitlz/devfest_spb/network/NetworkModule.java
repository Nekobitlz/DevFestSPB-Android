package com.nekobitlz.devfest_spb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class NetworkModule {

    private static final String BASE_URL = "https://storage.yandexcloud.net/devfestapi/";

    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);

    private Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson));

    private Retrofit retrofit = retrofitBuilder.build();

    public ServerApi serverApi = retrofit.create(ServerApi.class);
}
