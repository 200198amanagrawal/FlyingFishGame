package com.example.flyingfish;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView gameView;
    private Handler handler = new Handler();
    private final static long interval = 30;
    Timer timer = new Timer();
    public static int fishID=R.drawable.slidefish1_64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String extraFishID = String.valueOf(R.drawable.slidefish1_64);
        if(getIntent()!=null && getIntent().getExtras()!=null
                && getIntent().getExtras().get("Fish_ID").toString()!=null)
        {
            extraFishID=getIntent().getExtras().get("Fish_ID").toString();
        }
        fishID= Integer.parseInt(extraFishID);

        gameView = new FlyingFishView(this);
        setContentView(gameView);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();//this will start the view
                    }
                });
            }
        },0,interval);
    }
}
