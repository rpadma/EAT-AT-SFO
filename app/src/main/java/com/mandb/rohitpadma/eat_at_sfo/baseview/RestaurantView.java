package com.mandb.rohitpadma.eat_at_sfo.baseview;

import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;

/**
 * Created by rohitpadma on 3/14/18.
 */


public interface RestaurantView {

    public void setView(Restaurant restaurant);

    public void callRestaurant(String phoneNumber);

    public void shareRestaurant(String url);

    public void showToastMessage(String message);


}
