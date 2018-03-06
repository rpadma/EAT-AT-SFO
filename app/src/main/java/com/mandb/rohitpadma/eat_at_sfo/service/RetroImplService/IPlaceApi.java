package com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService;

import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rohitpadma on 3/5/18.
 */

public interface IPlaceApi {


    @GET("nearbysearch/json")
    Call<PlaceMarker> fetchPlaces(@Query("location") String location,
                                  @Query("radius") String radius,
                                  @Query("type") String type,
                                  @Query("key") String key
                                              );

    @GET("details/json")
    Call<Restaurant> fetchRestaurantDetails(@Query("placeid")String placeid,
                                            @Query("key") String key);

}

