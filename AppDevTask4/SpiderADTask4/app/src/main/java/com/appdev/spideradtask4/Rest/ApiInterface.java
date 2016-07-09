package com.appdev.spideradtask4.Rest;

import com.appdev.spideradtask4.Model.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by SasiDKM on 02-07-2016.
 */
public interface ApiInterface {
    @GET("/")
    Call<Movie> getMoT(@QueryMap Map<String,String> options);
}
