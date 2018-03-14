package com.mandb.rohitpadma.eat_at_sfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mandb.rohitpadma.eat_at_sfo.adapter.Imagedapter;
import com.mandb.rohitpadma.eat_at_sfo.adapter.ReviewAdapter;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Photos;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Reviews;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.rvlist)
    RecyclerView photolist;
    @BindView(R.id.RestaurantAddress)
    TextView raddress;
    ReviewAdapter ra;

    AlertDialog dialog;


    Result result;
    Restaurant restaurant;
    private IPlaceApi _placeservice;
    ArrayList<String> photourls =new ArrayList<>();

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
        Call<Restaurant> call=_placeservice.fetchRestaurantDetails(placeid,AppConfiguration.Key);
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


        if(restaurant.getResult().getPhotos()!=null) {
            for (Photos p : restaurant.getResult().getPhotos()) {
                photourls.add(AppConfiguration.BASE_PHOTO_URL + p.getPhoto_reference() + "&key=" + AppConfiguration.Key);
            }
        }
        rname.setText(restaurant.getResult().getName());
        if(restaurant.getResult().getRating()!=null)
        rrating.setRating(Float.valueOf(restaurant.getResult().getRating()));
        raddress.setText(restaurant.getResult().getFormatted_address());

        ra = new ReviewAdapter(this, R.layout.childreviewlayout,restaurant.getResult().getReviews());
        ra.setNotifyOnChange(true);
        reviewlist.setAdapter(ra);

        photolist=(RecyclerView)findViewById(R.id.rvlist);
        photolist.setHasFixedSize(true);
        photolist.setDrawingCacheEnabled(true);
        photolist.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        photolist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        Imagedapter ca = new Imagedapter(photourls,this);
        photolist.setAdapter(ca);
        ca.notifyDataSetChanged();


    }


    @OnClick(R.id.RestaurantContact)
     public void OnCallClick()
     {

   if(restaurant.getResult().getFormatted_phone_number()!=null) {
   // String num="9802502222";
    String dial = "tel:" + restaurant.getResult().getFormatted_phone_number();
    //String dial = "tel:"+num;
    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
     }
     else
     {
    Toast.makeText(RestaurantActivity.this,"No phone number",Toast.LENGTH_SHORT).show();
     }

     }

     @OnClick(R.id.RestaurantShare)
     public void onShareClick()
     {

         Intent sendIntent = new Intent();
         sendIntent.setAction(Intent.ACTION_SEND);
         sendIntent.putExtra(Intent.EXTRA_TEXT, restaurant.getResult().getUrl());
         sendIntent.setType("text/plain");
         startActivity(Intent.createChooser(sendIntent,"Share Location"));


     }










}
