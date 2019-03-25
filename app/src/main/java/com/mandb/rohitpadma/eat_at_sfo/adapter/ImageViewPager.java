package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ImageViewPager  extends PagerAdapter {

    private static final String CURRENT_POSITION = "CURRENT_POSITION";

    LayoutInflater layoutInflater;
    ViewPager viewPager;
    Context context;
    int currentPosition=0;
    ArrayList<String> colist;

    public ImageViewPager(ArrayList<String> colist, Context context, ViewPager viewPager) {
        this.colist=colist;
        this.context=context;
        this.viewPager=viewPager;
        this.layoutInflater=(LayoutInflater) this.context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // no action is needed here
            }

            @Override
            public void onPageSelected(int position) {
                // no action is need right now
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // no action is needed here
            }
        });

    }

    @Override
    public int getCount() {
        return this.colist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = this.layoutInflater.inflate(R.layout.image_item_viewer, container, false);
        v.setPadding(20,20,0,0);
        ImageView imageVew=v.findViewById(R.id.resImageView);
        Picasso.with(context).load(colist.get(position)).fit().into(imageVew);
        container.addView(v);
        return v;
    }
    Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_POSITION, currentPosition);
        return bundle;
    }

    void onRestoreInstanceState(Bundle state) {
        viewPager.setCurrentItem(state.getInt(CURRENT_POSITION), true);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void changePager(int position){
        viewPager.setCurrentItem(position,true);
        currentPosition=position;
    }
}