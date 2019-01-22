package com.lyriad.flitrio.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyriad.flitrio.Adapters.RecyclerViewFilmCategoryAdapter;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.R;

import java.util.ArrayList;
import java.util.List;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener{

    private Film myFilm;
    private List<Film> suggestionsList = new ArrayList<>();
    private Intent intent;

    TextView title, releaseDate, genre, description, duration;
    ImageView backButton, wallpaper;
    LinearLayout rateLayout, addToListLayout;
    RecyclerView suggestions;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        intent = getIntent();
        getFilm();
        getSuggestions();

        backButton = findViewById(R.id.film_back);
        wallpaper = findViewById(R.id.film_wallpaper);
        title = findViewById(R.id.film_title);
        releaseDate = findViewById(R.id.film_release_year);
        genre = findViewById(R.id.film_genre);
        duration = findViewById(R.id.film_duration);
        addToListLayout = findViewById(R.id.film_add_to_list);
        rateLayout = findViewById(R.id.film_rate);
        description = findViewById(R.id.film_description);
        suggestions = findViewById(R.id.film_suggestions);

        backButton.setOnClickListener(this);
        addToListLayout.setOnClickListener(this);
        rateLayout.setOnClickListener(this);

        suggestions.setAdapter(new RecyclerViewFilmCategoryAdapter(suggestionsList, this));
        suggestions.setLayoutManager(new GridLayoutManager(this, 3));

        String date = String.valueOf(myFilm.getReleaseDate().getDayOfMonth()) +
                " " + myFilm.getReleaseDate().getMonth().toString().charAt(0) +
                myFilm.getReleaseDate().getMonth().toString().toLowerCase().substring(1) +
                " " + String.valueOf(myFilm.getReleaseDate().getYear());

        Glide.with(this).load(myFilm.getWallpaperUrl()).into(wallpaper);
        title.setText(myFilm.getTitle());
        if (myFilm.getGenres().size() > 1) {
            genre.setText(myFilm.getGenres().get(0) + ", " + myFilm.getGenres().get(1));
        }else{
            genre.setText(myFilm.getGenres().get(0));
        }
        releaseDate.setText(date);
        duration.setText(myFilm.getDuration());
        description.setText(myFilm.getSummary());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.film_back:
                finish();
                break;
            case R.id.film_add_to_list:
                Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.film_rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getFilm(){
        for (Film aux : FirebaseData.getFilmList()){
            if (aux.getTitle().equals(intent.getStringExtra("Film"))){
                myFilm = aux;
                break;
            }
        }
    }

    private void getSuggestions(){
        int score = 0;

        for (Film auxFilm : FirebaseData.getFilmList()){
            auxFilm.setSuggestionScore(0);
            if ((!suggestionsList.contains(auxFilm)) && (auxFilm != myFilm)){
                for (String genre: myFilm.getGenres()){
                    if (auxFilm.getGenres().contains(genre)){
                        score++;
                    }
                }

                for (String cast: myFilm.getMainCast()){
                    if (auxFilm.getMainCast().contains(cast)){
                        score += 2;
                    }
                }

                if (auxFilm.getCountryOfOrigin().equals(myFilm.getCountryOfOrigin())){
                    score++;
                }

                if (score > 6){
                    suggestionsList.add(auxFilm);
                }
                if (suggestionsList.size() == 6){
                    break;
                }
            }
        }
    }
}
