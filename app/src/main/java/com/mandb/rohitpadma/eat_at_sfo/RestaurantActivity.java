package com.mandb.rohitpadma.eat_at_sfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mandb.rohitpadma.eat_at_sfo.model.Result;

public class RestaurantActivity extends AppCompatActivity {
    Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        if(getIntent().getExtras()!=null)
        {
            result=(Result) getIntent().getExtras().getParcelable("marker");
        }

        Toast.makeText(this,result.getName(),Toast.LENGTH_SHORT).show();
    }
}
