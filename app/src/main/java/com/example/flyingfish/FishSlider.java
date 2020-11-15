package com.example.flyingfish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FishSlider extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ImageView selectedImage;

    public static int selFish=R.drawable.slidefish1_64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_slider);
        viewPager2=findViewById(R.id.viewPagerSlider);
        selectedImage=findViewById(R.id.slideImageSelect);

        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.slidefish1));
        sliderItems.add(new SliderItem(R.drawable.slidefish2));
        sliderItems.add(new SliderItem(R.drawable.slidefish3));
        sliderItems.add(new SliderItem(R.drawable.slidefish4));
        sliderItems.add(new SliderItem(R.drawable.slidefish5));
        sliderItems.add(new SliderItem(R.drawable.slidefish6));
        sliderItems.add(new SliderItem(R.drawable.slidefish7));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FishSlider.this,GameOverActivity.class);
                intent.putExtra("Fish_ID",SliderAdapter.ImageId);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
