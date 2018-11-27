package com.tosinorojinmi.theophilus.agriwaves;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewLogo;
    Thread threadRow;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window   = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewLogo   = findViewById(R.id.splash);
        makeSimpleTaskAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void makeSimpleTaskAnim(){
        Animation myAnimation       = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        imageViewLogo.startAnimation(myAnimation);

        final Intent intent   = new Intent(this, HomeActivity.class);

        Thread thread   = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
