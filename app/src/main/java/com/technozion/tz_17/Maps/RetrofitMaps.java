package com.technozion.tz_17.Maps;

import com.technozion.tz_17.Maps.POJO.Example;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by acer on 16-09-2017.
 */

public interface RetrofitMaps {
    @GET("api/directions/json?key=AIzaSyAYgHuz5MBk9coyBhSX1R6EJahHjJWE4b8")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);
}
