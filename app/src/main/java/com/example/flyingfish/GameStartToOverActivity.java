package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class GameStartToOverActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        String fish_id = String.valueOf(R.drawable.slidefish1_64);
        imageView = findViewById(R.id.selectedImage);
        if (getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().get("Fish_ID").toString() != null) {
            fish_id = getIntent().getExtras().get("Fish_ID").toString();
        }
        final int intFishID = Integer.parseInt(fish_id);
        int scoreFromGame = 0;
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("score") != null) {
                scoreFromGame = Integer.parseInt(getIntent().getExtras().get("score").toString());
            }
        }
        imageView.setImageResource(intFishID);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameStartToOverActivity.this, FishSlider.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        TextView score = findViewById(R.id.score);
        score.setText("Score :" + scoreFromGame);
        Button startAgain = findViewById(R.id.StartAgain);
        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(GameStartToOverActivity.this, MainActivity.class);
                startGame.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startGame.putExtra("Fish_ID", intFishID);
                startActivity(startGame);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new FancyGifDialog.Builder(GameStartToOverActivity.this)
                .setTitle("Why Leaving so early :'-(")
                .setMessage("Want to quit or Stay?")
                .setNegativeBtnText("Stay")
                .setPositiveBtnBackground(R.color.cardview_shadow_start_color)
                .setPositiveBtnText("Quit")
                .setNegativeBtnBackground(R.color.cardview_shadow_start_color)
                .setGifResource(R.drawable.slidefish6)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        finish();
                    }
                }).OnNegativeClicked(new FancyGifDialogListener() {
            @Override
            public void OnClick() {

            }
        })
                .build();
    }
}
