package com.mandb.rohitpadma.eat_at_sfo.basepresenter;

/**
 * Created by rohitpadma on 3/14/18.
 */

public interface RestaurantPresenter {

    public void fetchRestaurantdata(String placeid);
    public void onCallClick();
    public void onShareClick();
    public void onCheckTiming();
    public void closeAlert();
}
