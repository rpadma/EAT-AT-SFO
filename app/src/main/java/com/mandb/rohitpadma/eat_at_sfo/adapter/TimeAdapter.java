package com.mandb.rohitpadma.eat_at_sfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mandb.rohitpadma.eat_at_sfo.R;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Reviews;
import com.mandb.rohitpadma.eat_at_sfo.util.Utility;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rohitpadma on 3/14/18.
 */

public class TimeAdapter extends ArrayAdapter<String> {


    String[] timinglist;
    Context mcontext;
    int mres;
    String weekday;

    public TimeAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.timinglist=objects;
        this.mcontext=context;
        this.mres=resource;
        this.weekday= Utility.getdayname();
    }



    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final String g=timinglist[position];
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(mres,parent,false);
            if(g.toLowerCase().contains(weekday))
            {
                convertView.setBackgroundColor(R.color.highlight);
            }
        }

        TextView timingtext=(TextView)convertView.findViewById(R.id.otiming);
        timingtext.setText(g);

        notifyDataSetChanged();
        return convertView;
    }


}
