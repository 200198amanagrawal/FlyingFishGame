package com.example.flyingfish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;
    public int fishes[]={R.drawable.slidefish1_64,R.drawable.slidefish2_64,R.drawable.slidefish3_64,
            R.drawable.slidefish4_64, R.drawable.slidefish5_64,R.drawable.slidefish6_64,
            R.drawable.slidefish7_64};
    public static int ImageId=R.drawable.slidefish1_64;
    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.slide_item_container,
                                parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, final int position) {
        holder.setImage(sliderItems.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ImageId=fishes[position];
                Toast.makeText(holder.imageView.getContext(), "Fish Selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        public SliderViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
        }

        void setImage(final SliderItem sliderItem)
        {
            imageView.setImageResource(sliderItem.getImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
