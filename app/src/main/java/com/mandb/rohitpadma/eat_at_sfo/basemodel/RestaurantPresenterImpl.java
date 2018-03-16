package com.mandb.rohitpadma.eat_at_sfo.basemodel;

import com.mandb.rohitpadma.eat_at_sfo.basepresenter.RestaurantPresenter;
import com.mandb.rohitpadma.eat_at_sfo.baseview.RestaurantView;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;
import com.mandb.rohitpadma.eat_at_sfo.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rohitpadma on 3/14/18.
 */

public class RestaurantPresenterImpl implements RestaurantPresenter {


    private IPlaceApi _placeservice;
    Restaurant restaurant;
    public RestaurantView restaurantView;

    public RestaurantPresenterImpl(RestaurantView restaurantView)
    {
        this.restaurantView=restaurantView;
       this._placeservice = (IPlaceApi) PlaceService.provideUserRestService();
    }


    @Override
    public void fetchRestaurantdata(String placeid) {

        if(Utility.isNetworkConnected()) {
            Call<Restaurant> call = _placeservice.fetchRestaurantDetails(placeid, AppConfiguration.Key);
            call.enqueue(new Callback<Restaurant>() {
                @Override
                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                    restaurant = response.body();
                    restaurantView.setView(restaurant);

                }

                @Override
                public void onFailure(Call<Restaurant> call, Throwable t) {

                    restaurantView.showToastMessage("Error occured during fetching Restaurant data");
                }
            });
        }
        else
        {
            restaurantView.showToastMessage("No Wifi or Network found");
        }
    }

    @Override
    public void onCallClick() {

        if(restaurant.getResult().getFormatted_phone_number()!=null) {
            restaurantView.callRestaurant(restaurant.getResult().getFormatted_phone_number());
        }
        else
        {
            restaurantView.showToastMessage("No phone number found");
        }
    }

    @Override
    public void onShareClick() {

        if(restaurant.getResult().getUrl()!=null)
        {
            restaurantView.shareRestaurant(restaurant.getResult().getUrl());
        }
        else
        {
            restaurantView.showToastMessage("No url found");
        }

    }

    @Override
    public void onCheckTiming() {

        if(restaurant.getResult().getOpening_hours().getWeekday_text()!=null)
        {
            restaurantView.showTiming(restaurant.getResult().getOpening_hours().getWeekday_text());
        }
        else
        {
            restaurantView.showToastMessage("No open hours found");
        }
    }

    @Override
    public void closeAlert() {

        restaurantView.closeAlert();
    }


}
