package com.lyriad.flitrio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.lyriad.flitrio.Activities.TVSeriesActivity;
import com.lyriad.flitrio.Adapters.Models.FilmCategoryModel;
import com.lyriad.flitrio.Adapters.RecyclerViewFilmCategoriesAdapter;
import com.lyriad.flitrio.Adapters.SliderPageAdapter;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.Slide;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.Classes.TVSeries;
import com.lyriad.flitrio.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private List<Slide> trendingSlides = new ArrayList<>();
    private List<FilmCategoryModel> categories = new ArrayList<>();
    ViewPager trending;
    ImageView addButton, infoButton;
    Button playButton;
    RecyclerView filmCategories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadTrendingSlides();
        loadCategories();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        trending = homeView.findViewById(R.id.home_trending_slider);
        addButton = homeView.findViewById(R.id.home_trending_add_to_list);
        playButton = homeView.findViewById(R.id.home_trending_play);
        infoButton = homeView.findViewById(R.id.home_trending_open);
        filmCategories = homeView.findViewById(R.id.home_film_categories);

        addButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);

        SliderPageAdapter trendingAdapter = new SliderPageAdapter(getActivity(), trendingSlides);
        RecyclerViewFilmCategoriesAdapter categoriesAdapter = new RecyclerViewFilmCategoriesAdapter(
                categories, getActivity());
        trending.setAdapter(trendingAdapter);
        filmCategories.setAdapter(categoriesAdapter);
        filmCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmCategories.setNestedScrollingEnabled(false);

        return homeView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_trending_add_to_list:
                Toast.makeText(getActivity(), "Added to list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_trending_play:
                Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_trending_open:
                Bundle args = new Bundle();
                Fragment tvFragment = new TVSeriesFragment();
                for (TVSeries aux : FirebaseData.getTVSeriesList()){
                    if (aux.getTitle().equals(trendingSlides.get(trending.getCurrentItem()).getTitle())) {
                        args.putString("TV Series", trendingSlides.get(trending.getCurrentItem()).getTitle());
                        break;
                    }
                }
                tvFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        tvFragment).addToBackStack(null).commit();
                break;
        }
    }

    private void loadTrendingSlides() {

        for (TVSeries tvShow : FirebaseData.getTVSeriesList()) {
            trendingSlides.add(new Slide(tvShow.getTitle(), tvShow.getWallpaperUrl()));
        }
    }

    private void loadCategories(){

        for (String genre : StaticData.getGenres()){
            List<Film> auxList = new ArrayList<>();
            for (Film aux : FirebaseData.getFilmList()){
                if (aux.getGenres().contains(genre)){
                    auxList.add(aux);
                }
            }
            if (!auxList.isEmpty()){
                FilmCategoryModel categoryModel = new FilmCategoryModel(genre, auxList);
                categories.add(categoryModel);
            }
        }
    }
}
