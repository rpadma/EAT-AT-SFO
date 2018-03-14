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
import com.mandb.rohitpadma.eat_at_sfo.adapter.TimeAdapter;
import com.mandb.rohitpadma.eat_at_sfo.basemodel.RestaurantPresenterImpl;
import com.mandb.rohitpadma.eat_at_sfo.baseview.RestaurantView;
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

public class RestaurantActivity extends AppCompatActivity implements RestaurantView {

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
    @BindView(R.id.RestaurantTimings)
    ListView timinglist;

    ReviewAdapter ra;
    TimeAdapter ta;

    AlertDialog dialog;


    Result result;
    ArrayList<String> photourls =new ArrayList<>();
    RestaurantPresenterImpl restaurantPresenter;

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

        restaurantPresenter=new RestaurantPresenterImpl(this);
        restaurantPresenter.fetchRestaurantdata(result.getPlaceId());


    }


    @Override
    public void setView(Restaurant restaurant)
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


        ta=new TimeAdapter(this,R.layout.childtimingview,restaurant.getResult().getOpening_hours().getWeekday_text());
        ta.setNotifyOnChange(true);
        timinglist.setAdapter(ta);

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
     public void onCallClick()
     {

         restaurantPresenter.onCallClick();
     }


     @OnClick(R.id.RestaurantShare)
     public void onShareClick()
     {
         restaurantPresenter.onShareClick();
     }


    @Override
    public void callRestaurant(String phoneNumber) {


            String dial = "tel:" + phoneNumber; //restaurant.getResult().getFormatted_phone_number();
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));


    }

    @Override
    public void shareRestaurant(String url) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"Share Location"));
    }

    @Override
    public void showToastMessage(String message) {

        Toast.makeText(RestaurantActivity.this,message,Toast.LENGTH_SHORT).show();
    }



}
