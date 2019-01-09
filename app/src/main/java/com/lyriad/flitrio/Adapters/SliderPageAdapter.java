package com.lyriad.flitrio.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyriad.flitrio.Classes.Slide;
import com.lyriad.flitrio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderPageAdapter extends PagerAdapter {

    private Context myContext;
    private List<Slide> slides;

    public SliderPageAdapter(Context myContext, List<Slide> slides) {
        this.myContext = myContext;
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_item, null);

        ImageView slideImage = slideLayout.findViewById(R.id.trending_image);
        TextView slideText = slideLayout.findViewById(R.id.trending_title);

        Glide.with(slideLayout).load(Uri.parse(slides.get(position).getImage())).into(slideImage);
        slideText.setText(slides.get(position).getTitle());

        container.addView(slideLayout);

        return slideLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
