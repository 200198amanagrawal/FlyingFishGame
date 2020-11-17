package com.example.flyingfish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView gameView;
    private Handler handler = new Handler();
    private final static long interval = 30;
    Timer timer = new Timer();
    public static int fishID = R.drawable.slidefish1_64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String extraFishID = String.valueOf(R.drawable.slidefish1_64);
        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().get("Fish_ID").toString() != null) {
            extraFishID = getIntent().getExtras().get("Fish_ID").toString();
        }
        fishID = Integer.parseInt(extraFishID);

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
        }, 0, interval);
    }

    @Override
    public void onBackPressed() {
        new FancyGifDialog.Builder(MainActivity.this)
                .setTitle("Why Leaving so early :'-(?")
                .setMessage("All your current scores would become 0!! Want to quit or Stay?")
                .setNegativeBtnText("Stay")
                .setPositiveBtnBackground(R.color.cardview_shadow_start_color)
                .setPositiveBtnText("Quit")
                .setNegativeBtnBackground(R.color.cardview_shadow_start_color)
                .setGifResource(R.drawable.slidefish6)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent gameOverIntent = new Intent(MainActivity.this, GameStartToOverActivity.class);
                        gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        gameOverIntent.putExtra("score", 0);
                        gameOverIntent.putExtra("Fish_ID", MainActivity.fishID);
                        startActivity(gameOverIntent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }).OnNegativeClicked(new FancyGifDialogListener() {
            @Override
            public void OnClick() {

            }
        })
                .build();
    }
}
