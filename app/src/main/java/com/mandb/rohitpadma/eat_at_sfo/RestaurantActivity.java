package com.mandb.rohitpadma.eat_at_sfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mandb.rohitpadma.eat_at_sfo.adapter.ReviewAdapter;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Reviews;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {

    @BindView(R.id.RestaurantName)
    TextView rname;
    @BindView(R.id.RestaurantRating)
    RatingBar rrating;
    @BindView(R.id.RestaurantContact)
    ImageView rcall;
    @BindView(R.id.ReviewList)
    ListView reviewlist;

    ReviewAdapter ra;


    Result result;
    Restaurant restaurant;
    private IPlaceApi _placeservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        if(getIntent().getExtras()!=null)
        {
            result=(Result) getIntent().getExtras().getParcelable("marker");
        }

        _placeservice = (IPlaceApi) PlaceService.provideUserRestService();



        fetchRestaurantdata(result.getPlaceId());
    }

    public void fetchRestaurantdata(String placeid)
    {
        Call<Restaurant> call=_placeservice.fetchRestaurantDetails(placeid,"AIzaSyBcLZtBC-2jSDG8iiZpECV_yWpUqaGeDTk");
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                restaurant=response.body();
                setview(restaurant);

            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    public void setview(Restaurant restaurant)
    {
        rname.setText(restaurant.getResult().getName());
        rrating.setRating(Float.valueOf(restaurant.getResult().getRating()));


        ra = new ReviewAdapter(this, R.layout.childreviewlayout,restaurant.getResult().getReviews());
        ra.setNotifyOnChange(true);
        reviewlist.setAdapter(ra);



    }


    @OnClick(R.id.RestaurantContact)
     public void OnCallClick()
     {


     }


}
