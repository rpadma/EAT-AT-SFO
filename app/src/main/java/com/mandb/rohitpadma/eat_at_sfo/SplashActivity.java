package com.mandb.rohitpadma.eat_at_sfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Thread runnerlog=new Thread()
        {
            public void run()
            {
                try
                {
                    int logoTimer=0;
                    while(logoTimer<2000)
                    {
                        sleep(100);
                        logoTimer=logoTimer+100;
                    }

                    Intent i=new Intent(SplashActivity.this,MapsActivity.class);
                    startActivity(i);

                }catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }finally
                {
                    finish();
                }
            }

        };

        runnerlog.start();
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindDrawables(findViewById(R.id.splashlayout));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
