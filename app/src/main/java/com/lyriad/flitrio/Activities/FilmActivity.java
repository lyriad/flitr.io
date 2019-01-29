package com.lyriad.flitrio.Activities;

import androidx.annotation.NonNull;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lyriad.flitrio.Adapters.RecyclerViewFilmCategoryAdapter;
import com.lyriad.flitrio.Classes.Film;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore fireDatabase;
    private FirebaseUser currentUser;
    private Film myFilm;
    private List<Film> suggestionsList = new ArrayList<>();
    private Intent intent;

    TextView title, releaseDate, genre, description, duration;
    ImageView backButton, wallpaper, searchButton, homeButton;
    CircularImageView profileImage;
    LinearLayout rateLayout;
    RelativeLayout modifyListLayout, addToListLayout, removeFromListLayout;
    RecyclerView suggestions;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        fireDatabase = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        getFilm();
        getSuggestions();

        profileImage = findViewById(R.id.film_profile_picture);
        searchButton = findViewById(R.id.film_search_button);
        homeButton = findViewById(R.id.film_home);

        backButton = findViewById(R.id.film_back);
        wallpaper = findViewById(R.id.film_wallpaper);
        title = findViewById(R.id.film_title);
        releaseDate = findViewById(R.id.film_release_year);
        genre = findViewById(R.id.film_genre);
        duration = findViewById(R.id.film_duration);
        modifyListLayout = findViewById(R.id.film_modify_list);
        addToListLayout = findViewById(R.id.film_add_to_list);
        removeFromListLayout = findViewById(R.id.film_remove_from_list);
        rateLayout = findViewById(R.id.film_rate);
        description = findViewById(R.id.film_description);
        suggestions = findViewById(R.id.film_suggestions);

        profileImage.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        modifyListLayout.setOnClickListener(this);
        rateLayout.setOnClickListener(this);

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

        if (FirebaseData.getCurrentUser().getList().contains(myFilm.getTitle())){
            addToListLayout.setVisibility(View.INVISIBLE);
            removeFromListLayout.setVisibility(View.VISIBLE);
        }else{
            addToListLayout.setVisibility(View.VISIBLE);
            removeFromListLayout.setVisibility(View.INVISIBLE);
        }

        suggestions.setAdapter(new RecyclerViewFilmCategoryAdapter(suggestionsList, this));
        suggestions.setLayoutManager(new GridLayoutManager(this, 3));

        Glide.with(this).load(currentUser.getPhotoUrl()).into(profileImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.film_back:
                finish();
                break;
            case R.id.film_modify_list:
                if (FirebaseData.getCurrentUser().getList().contains(myFilm.getTitle())){
                    removeFilmFromList();
                }else{
                    saveFilmToList();
                }
                break;
            case R.id.film_rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.film_search_button:
                startActivity(new Intent(FilmActivity.this, SearchActivity.class));
                break;
            case R.id.film_profile_picture:
                startActivity(new Intent(FilmActivity.this, UserProfileActivity.class));
                break;
            case R.id.film_home:
                Intent intent = new Intent(FilmActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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

    private void saveFilmToList(){

        FirebaseData.getCurrentUser().getList().add(myFilm.getTitle());

        fireDatabase.collection(StaticData.getUserCollection()).document(currentUser.getEmail()).
                update("List", FirebaseData.getCurrentUser().getList()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Added to list", Toast.LENGTH_SHORT).show();
            }
        });

        addToListLayout.setVisibility(View.INVISIBLE);
        removeFromListLayout.setVisibility(View.VISIBLE);
    }

    private void removeFilmFromList(){

        FirebaseData.getCurrentUser().getList().remove(myFilm.getTitle());

        fireDatabase.collection(StaticData.getUserCollection()).document(currentUser.getEmail()).
                update("List", FirebaseData.getCurrentUser().getList()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Removed from list", Toast.LENGTH_SHORT).show();
            }
        });

        addToListLayout.setVisibility(View.VISIBLE);
        removeFromListLayout.setVisibility(View.INVISIBLE);
    }
}
