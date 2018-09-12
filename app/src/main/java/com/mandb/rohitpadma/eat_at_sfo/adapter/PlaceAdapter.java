package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends  RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    List<Result> placelist;
    Context mContext;

    public PlaceAdapter(Context context,List<Result> placelist) {
        this.mContext=context;
        this.placelist=placelist;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        PlaceAdapter.ViewHolder vh = new PlaceAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {

        Result place=placelist.get(position);
        holder.rtextName.setText(place.getName());
        holder.ratingBar.setRating(Float.valueOf(String.valueOf(place.getRating())));

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return placelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView rtextName;

        public ViewHolder(View itemView) {
            super(itemView);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar);
            rtextName=(TextView)itemView.findViewById(R.id.rrating);

        }
    }
}
