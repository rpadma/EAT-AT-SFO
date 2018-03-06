package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitpadma on 3/6/18.
 */

public class Imagedapter extends RecyclerView.Adapter<Imagedapter.ViewHolder>{


    ArrayList<String> colist;
    Callback callback;

    public Imagedapter(ArrayList<String> colist,Callback callback)
    {
        this.colist=colist;
        this.callback=callback;

    }


public class ViewHolder extends RecyclerView.ViewHolder {


    public ImageView imgview;


    public ViewHolder(View v) {

        super(v);
        imgview=(ImageView)v.findViewById(R.id.RestaurantPhoto);


      }

     }


        public static interface Callback{
          void showPhoto(String position);
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

            // holder.imagecview.setBackground(null);
            Picasso.with(holder.imgview.getContext()).load(s).fit().into(holder.imgview);
        }


        holder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.showPhoto(colist.get(position));


            }
        });



    }

    @Override
    public int getItemCount() {
        return colist.size();
    }
}
