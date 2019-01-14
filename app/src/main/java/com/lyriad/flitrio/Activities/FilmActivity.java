package com.lyriad.flitrio.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.R;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener{

    private Film film;
    private Intent intent;

    TextView title, releaseDate, genre, description;
    ImageView backButton, wallpaper;
    LinearLayout rateLayout, addToListLayout;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        intent = getIntent();
        getFilm();

        backButton = findViewById(R.id.film_back);
        wallpaper = findViewById(R.id.film_wallpaper);
        title = findViewById(R.id.film_title);
        releaseDate = findViewById(R.id.film_release_year);
        genre = findViewById(R.id.film_genre);
        addToListLayout = findViewById(R.id.film_add_to_list);
        rateLayout = findViewById(R.id.film_rate);
        description = findViewById(R.id.film_description);

        backButton.setOnClickListener(this);
        addToListLayout.setOnClickListener(this);
        rateLayout.setOnClickListener(this);

        String date = String.valueOf(film.getReleaseDate().getDayOfMonth()) +
                " " + film.getReleaseDate().getMonth().toString().charAt(0) +
                film.getReleaseDate().getMonth().toString().toLowerCase().substring(1) +
                " " + String.valueOf(film.getReleaseDate().getYear());

        Glide.with(this).load(film.getWallpaperUrl()).into(wallpaper);
        title.setText(film.getTitle());
        genre.setText(film.getGenres().get(0) + ", " + film.getGenres().get(1));
        releaseDate.setText(date);
        description.setText(film.getSummary());
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
                film = aux;
                break;
            }
        }
    }
}
