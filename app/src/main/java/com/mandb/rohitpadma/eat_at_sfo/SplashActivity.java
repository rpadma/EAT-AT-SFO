package com.mandb.rohitpadma.eat_at_sfo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView mLottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
       mLottieAnimationView = findViewById(R.id.lottie_animation_view);

        LottieComposition.Factory.fromAssetFileName(this, "lottie.json", composition -> {
            mLottieAnimationView.setComposition(composition);
            mLottieAnimationView.playAnimation();  });

       Thread runnerlog=new Thread()
        {
            public void run()
            {
                try
                {


                    int logoTimer=0;
                    while(logoTimer<4000)
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

       // bindAnim();
    }

    public void bindAnim(){
        new Handler().post(() -> {

        });


    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindDrawables(findViewById(R.id.splashlayout));
        mLottieAnimationView.cancelAnimation();
        mLottieAnimationView = null;
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
