package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread=new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(5000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent startGame = new Intent(SplashScreen.this, GameOverActivity.class);
                    startGame.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startGame);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
