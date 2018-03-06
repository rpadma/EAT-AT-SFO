package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Reviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rohitpadma on 3/6/18.
 */


public class ReviewAdapter extends ArrayAdapter<Reviews> {

    Reviews[] flist;
    Context mcontext;
    int mres;



    public ReviewAdapter(Context context, int resource, Reviews[] objects) {
        super(context, resource, objects);
        this.flist=objects;
        this.mcontext=context;
        this.mres=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(mres,parent,false);
        }
        final Reviews g=flist[position];


        ImageView iv=(ImageView)convertView.findViewById(R.id.reviewphoto);
        TextView reviewtext=(TextView)convertView.findViewById(R.id.reviewtext);
        TextView username=(TextView)convertView.findViewById(R.id.username);
        TextView userrating=(TextView)convertView.findViewById(R.id.userrating);

        reviewtext.setText(g.getText());
        Picasso.with(mcontext).load(g.getProfile_photo_url()).fit().placeholder(R.drawable.avatar).into(iv);
         username.setText(g.getAuthor_name());
         userrating.setText("Rating: "+g.getRating());



        notifyDataSetChanged();

        return convertView;


    }


}