package com.mandb.rohitpadma.eat_at_sfo.Service.RetroImplService;

import com.mandb.rohitpadma.eat_at_sfo.Model.PlaceMarker;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rohitpadma on 3/5/18.
 */

public interface IPlaceApi {


    @GET("/nearbysearch/json")
    Observable<List<PlaceMarker>> fetchPlaces(@Field("location") String location,
                                              @Field("radius") String radius,
                                              @Field("type") String type,
                                              @Field("key") String key
                                              );


}

