package com.nekobitlz.devfest_spb.network;

import com.nekobitlz.devfest_spb.data.SpeakerInformation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerApi {
    @GET("data.json")
    Call<Speaker> getSpeakers();
}
