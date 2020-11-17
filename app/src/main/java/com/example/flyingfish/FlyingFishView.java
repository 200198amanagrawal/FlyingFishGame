package com.example.flyingfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.List;


public class FlyingFishView extends View {
    private Bitmap[] fish = new Bitmap[2];

    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int yellowX;
    private int yellowY;
    private float speedMeter = 0.0f;
    private Paint yellowPaint = new Paint();

    private int greenX;
    private int greenY;
    private Paint greenPaint = new Paint();

    private int redX;
    private int redY;
    private Paint redPaint = new Paint();

    private float score;
    int lifecounterOfFish;

    private boolean touch = false;

    private Paint scorePaint = new Paint();
    private List<Bitmap> life = new ArrayList<>();
    private List<Bitmap> death = new ArrayList<>();
    private List<Integer> backgroundImages = new ArrayList<>();
    private float lifeIncrement;
    private int bgIncrement = 0;

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), MainActivity.fishID);

        backgroundImages.add(R.drawable.bg0);
        backgroundImages.add(R.drawable.bg1);
        backgroundImages.add(R.drawable.bg2);
        backgroundImages.add(R.drawable.bg3);
        backgroundImages.add(R.drawable.bg4);
        backgroundImages.add(R.drawable.bg5);
        backgroundImages.add(R.drawable.bg6);
        backgroundImages.add(R.drawable.bg7);
        backgroundImages.add(R.drawable.bg8);
        backgroundImages.add(R.drawable.bg9);
        backgroundImages.add(R.drawable.bg10);
        backgroundImages.add(R.drawable.bg11);
        backgroundImages.add(R.drawable.bg12);
        backgroundImages.add(R.drawable.bg13);
        backgroundImages.add(R.drawable.bg14);
        backgroundImages.add(R.drawable.bg15);
        backgroundImages.add(R.drawable.bg16);
        backgroundImages.add(R.drawable.bg17);
        backgroundImages.add(R.drawable.bg19);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        life.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts));
        death.add(BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey));

        fishY = 550;//some value to start with
        score = 60;
        lifecounterOfFish = 3;
        lifeIncrement = 0.0f;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(backgroundImages.get(bgIncrement / 5));
        bgIncrement++;
        if (bgIncrement == 95) {
            bgIncrement = 0;
        }

        speedMeter += 1.0 / 100.0;
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        setTimerScore();
        canvas.drawText("Score : " + (int) score, 20, 70, scorePaint);

        int minFishY = fish[0].getHeight();//192
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;// this will go top so if canvasheight is 2000 than its 350
        fishY = fishY + fishSpeed;//this will change to up and down one click -22 then slowly to +2 on draw runs all the time
        if (fishY < minFishY) {
            fishY = minFishY;
        } else if (fishY > maxFishY) {
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;

        canvas.drawBitmap(fish[0], fishX, fishY, null);


        int yellowSpeed = 16;
        yellowX = yellowX - yellowSpeed - (int) speedMeter;
        if (hitBallChecker(yellowX, yellowY)) {
            score += 5;
            yellowX = yellowX - 100;
        }
        if (yellowX < 0) {
            yellowX = canvasWidth + 23;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        int greenSpeed = 20;
        greenX = greenX - greenSpeed - (int) speedMeter;
        if (hitBallChecker(greenX, greenY)) {
            score += 10;
            greenX = greenX - 100;
        }
        if (greenX < 0) {
            greenX = canvasWidth + 23;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }


        int redSpeed = 20;
        redX = redX - redSpeed - (int) speedMeter;
        if (hitBallChecker(redX, redY)) {
            score -= 10;
            redX = redX - 100;
            lifecounterOfFish--;
        }
        if ((int) score == 0 || lifecounterOfFish == 0) {
            new FancyGifDialog.Builder(getContext())
                    .setTitle("You were a bit slow but its Ok") // You can also send title like R.string.from_resources
                    .setMessage("Want to try again or quit?") // or pass like R.string.description_from_resources
                    .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                    .setPositiveBtnBackground(R.color.cardview_shadow_start_color) // or pass it like R.color.positiveButton
                    .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                    .setNegativeBtnBackground(R.color.cardview_shadow_start_color) // or pass it like R.color.negativeButton
                    .setGifResource(R.drawable.slidefish6)//Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Intent gameOverIntent = new Intent(getContext(), MainActivity.class);
                            gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            gameOverIntent.putExtra("Fish_ID", MainActivity.fishID);
                            getContext().startActivity(gameOverIntent);
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                            Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                            Intent gameOverIntent = new Intent(getContext(), GameStartToOverActivity.class);
                            gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            gameOverIntent.putExtra("score", (int) score);
                            gameOverIntent.putExtra("Fish_ID", MainActivity.fishID);
                            getContext().startActivity(gameOverIntent);
                        }
                    })
                    .build();
        }
        if (redX < 0) {
            redX = canvasWidth + 23;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(yellowX, yellowY, 15, yellowPaint);
        canvas.drawCircle(greenX, greenY, 22, greenPaint);
        canvas.drawCircle(redX, redY, 15, redPaint);

        lifeIncrementerAt10secs();
        int y = 30;
        for (int i = 0; i < lifecounterOfFish; i++) {
            int x = (int) (420 + life.get(0).getWidth() * 1.2 * 1);
            if (i < lifecounterOfFish) {
                if (lifecounterOfFish == 1) {
                    canvas.drawBitmap(death.get(0), 420, y, null);
                } else {
                    canvas.drawBitmap(life.get(0), 420, y, null);
                }
                canvas.drawText(Integer.toString(lifecounterOfFish), x, 80, scorePaint);
            }
        }
    }

    private void lifeIncrementerAt10secs() {

        lifeIncrement += 1.0 / 25.0;
        if (((int) lifeIncrement) == 100) {
            lifecounterOfFish++;
            lifeIncrement = 0.0f;
            Toast.makeText(getContext(), "Life Increased because of 1 min in game", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTimerScore() {

        score -= 1.0 / 25.0;
    }

    public boolean hitBallChecker(int x, int y) {
        return fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -25;//if we touch this will push up to 22 pixles up
        }
        return false;
    }


}
