package com.appdev.spideradtask4.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public class ApiClient {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://omdbapi.com";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
