package com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService;

import com.mandb.rohitpadma.eat_at_sfo.model.Example;
import com.mandb.rohitpadma.eat_at_sfo.model.PlaceMarker;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rohitpadma on 3/5/18.
 */

public interface IPlaceApi {


    @GET("nearbysearch/json")
    Call<Example> fetchPlaces(@Query("location") String location,
                              @Query("radius") String radius,
                              @Query("type") String type,
                              @Query("key") String key
                                              );


}

