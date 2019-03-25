package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.mandb.rohitpadma.eat_at_sfo.RestaurantActivity;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Photo;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Photos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rohitpadma on 3/6/18.
 */

public class Imagedapter extends RecyclerView.Adapter<Imagedapter.ViewHolder>{


    ArrayList<String> colist;
    Callback callback;
    AlertDialog dialog;
    Context context;
    int cu=0;

    public Imagedapter(ArrayList<String> colist,Context context)
    {
        this.colist=colist;
        this.context=context;
    }

public class ViewHolder extends RecyclerView.ViewHolder {


    public ImageView imgview;


    public ViewHolder(View v) {
        super(v);
        imgview=(ImageView)v.findViewById(R.id.RestaurantPhoto);
      }
     }

     public static interface Callback{
          void showPhoto(int position);
        }

    @Override
    public Imagedapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.childimageview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Imagedapter.ViewHolder holder, final int position) {

        String s = colist.get(position);

        if (s.length() > 0) {
            Picasso.with(holder.imgview.getContext()).load(s).fit().placeholder(R.drawable.imageloading).into(holder.imgview);
        }


        holder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(holder.imgview.getContext());
                builder.setView(R.layout.image_viewer);
                dialog = builder.create();
                dialog.getWindow().setLayout(500,500);
                dialog.show();
                final ViewPager viewPager=(ViewPager)dialog.findViewById(R.id.imagePager);
                final TabLayout tabIndicator=(TabLayout)dialog.findViewById(R.id.tab_indicator);
                ImageViewPager imageViewPager=new ImageViewPager(colist,context,viewPager);
                viewPager.setAdapter(imageViewPager);
                viewPager.setPageMargin(20);
                tabIndicator.setupWithViewPager(viewPager, true);
                tabIndicator.setTabGravity(TabLayout.GRAVITY_CENTER);
                ViewGroup.LayoutParams layoutParams=tabIndicator.getLayoutParams();
                layoutParams.width=getItemCount()*50;
                tabIndicator.setLayoutParams(layoutParams);
                dialog.setCancelable(true);

            }
        });



    }


    public void setimage(int position)
    {
        final int currentposition=position;
        cu=position;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(R.layout.image_viewer);
        dialog = builder.create();
        dialog.show();

        final ViewPager viewPager=(ViewPager)dialog.findViewById(R.id.imagePager);
        ImageViewPager imageViewPager=new ImageViewPager(colist,context,viewPager);
        viewPager.setAdapter(imageViewPager);

      /*  final ImageView iv=(ImageView)dialog.findViewById(R.id.photoview);
        setphotoview(iv,0);

        final ImageView previousiv=(ImageView)dialog.findViewById(R.id.ivprevious);
        ImageView nextiv=(ImageView)dialog.findViewById(R.id.ivnext);

        previousiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setphotoview(iv,-1);

            }
        });

        nextiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                setphotoview(iv,1);
            }
        }); */


    }
    public void setphotoview(ImageView iv,int val)
    {

        if(cu+val>=0 && val==-1)
        {
            cu=cu+val;
            Picasso.with(context).load(colist.get(cu)).placeholder(R.drawable.imageloading).fit().into(iv);
        }
        else if(cu+val<colist.size() && val==1){
            cu=cu+val;
            Picasso.with(context).load(colist.get(cu)).placeholder(R.drawable.imageloading).fit().into(iv);
        }
        else
        {
            Picasso.with(context).load(colist.get(cu)).placeholder(R.drawable.imageloading).fit().into(iv);
        }
    }

    @Override
    public int getItemCount() {
        return colist.size();
    }
}
