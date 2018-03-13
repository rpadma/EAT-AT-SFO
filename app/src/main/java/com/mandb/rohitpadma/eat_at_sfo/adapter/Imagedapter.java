package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.app.AlertDialog;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

            // holder.imagecview.setBackground(null);
            Picasso.with(holder.imgview.getContext()).load(s).fit().into(holder.imgview);
        }


        holder.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //callback.showPhoto(position);

           setimage(position);

            }
        });



    }


    public void setimage(int position)
    {
        final int currentposition=position;
        cu=position;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(R.layout.imagelayout);
        dialog = builder.create();
        dialog.show();

        final ImageView iv=(ImageView)dialog.findViewById(R.id.photoview);
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
        });
    }
    public void setphotoview(ImageView iv,int val)
    {

        if(cu+val>=0 && val==-1)
        {
            cu=cu+val;
            Picasso.with(context).load(colist.get(cu)).fit().placeholder(R.drawable.avatar).into(iv);
        }
        else if(cu+val<colist.size() && val==1){
            cu=cu+val;
            Picasso.with(context).load(colist.get(cu)).fit().placeholder(R.drawable.avatar).into(iv);
        }
        else
        {
            Picasso.with(context).load(colist.get(cu)).fit().placeholder(R.drawable.avatar).into(iv);
        }
    }

    @Override
    public int getItemCount() {
        return colist.size();
    }
}
