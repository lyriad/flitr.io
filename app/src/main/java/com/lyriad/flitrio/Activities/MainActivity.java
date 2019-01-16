package com.lyriad.flitrio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lyriad.flitrio.Adapters.Models.FilmCategoryModel;
import com.lyriad.flitrio.Adapters.RecyclerViewFilmCategoriesAdapter;
import com.lyriad.flitrio.Adapters.SliderPageAdapter;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.Slide;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.Classes.TVSeries;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth fireAuthentication;
    FirebaseUser currentUser;
    List<Slide> trendingSlides = new ArrayList<>();
    List<FilmCategoryModel> categories = new ArrayList<>();

    ViewPager trending;
    ImageView searchButton, addButton, infoButton;
    Button playButton;
    CircularImageView profileImage;
    RecyclerView filmCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadTrendingSlides();
        loadCategories();

        fireAuthentication = FirebaseAuth.getInstance();
        currentUser = fireAuthentication.getCurrentUser();

        trending = findViewById(R.id.main_trending_slider);
        profileImage = findViewById(R.id.main_profile_picture);
        searchButton = findViewById(R.id.main_search_button);
        addButton = findViewById(R.id.trending_add_to_list);
        playButton = findViewById(R.id.trending_play);
        infoButton = findViewById(R.id.trending_open);
        filmCategories = findViewById(R.id.main_film_categories);

        profileImage.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);

        SliderPageAdapter trendingAdapter = new SliderPageAdapter(this, trendingSlides);
        RecyclerViewFilmCategoriesAdapter categoriesAdapter = new RecyclerViewFilmCategoriesAdapter(
                categories, this);

        trending.setAdapter(trendingAdapter);
        filmCategories.setAdapter(categoriesAdapter);
        filmCategories.setLayoutManager(new LinearLayoutManager(this));
        filmCategories.setNestedScrollingEnabled(false);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fireAuthentication.getCurrentUser() == null){
            FirebaseData.resetUser();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_profile_picture:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                break;
            case R.id.main_search_button:
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.trending_add_to_list:
                Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.trending_play:
                Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
                break;
            case R.id.trending_open:
                Intent trendingIntent = getSelectedIntent();
                startActivity(trendingIntent);
                break;
        }
    }

    private void loadTrendingSlides() {

        for (TVSeries tvShow : FirebaseData.getTVSeriesList()) {
            trendingSlides.add(new Slide(tvShow.getTitle(), tvShow.getWallpaperUrl()));
        }
    }

    private Intent getSelectedIntent(){
        Intent intent = null;
        boolean foundFilm = false;

        for (Film aux : FirebaseData.getFilmList()){
            if (aux.getTitle().equals(trendingSlides.get(trending.getCurrentItem()).getTitle())){
                intent = new Intent(MainActivity.this, FilmActivity.class);
                intent.putExtra("Film", trendingSlides.get(trending.getCurrentItem()).getTitle());
                foundFilm = true;
            }
        }

        if (!foundFilm){
            for (TVSeries aux : FirebaseData.getTVSeriesList()){
                if (aux.getTitle().equals(trendingSlides.get(trending.getCurrentItem()).getTitle())) {
                    intent = new Intent(MainActivity.this, TVSeriesActivity.class);
                    intent.putExtra("TV Series", trendingSlides.get(trending.getCurrentItem()).getTitle());
                }
            }
        }

        return intent;
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
