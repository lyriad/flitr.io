package com.lyriad.flitrio.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class FilmFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore fireDatabase;
    private FirebaseUser currentUser;
    private Film myFilm;
    private List<Film> suggestionsList = new ArrayList<>();

    private TextView title, releaseDate, genre, description, duration;
    private ImageView backButton, wallpaper, playButton;
    private LinearLayout rateLayout;
    private RelativeLayout modifyListLayout, addToListLayout, removeFromListLayout;
    private RecyclerView suggestions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireDatabase = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        getFilm();
        getSuggestions();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);

        backButton = view.findViewById(R.id.film_back);
        wallpaper = view.findViewById(R.id.film_wallpaper);
        playButton = view.findViewById(R.id.film_play);
        title = view.findViewById(R.id.film_title);
        releaseDate = view.findViewById(R.id.film_release_year);
        genre = view.findViewById(R.id.film_genre);
        duration = view.findViewById(R.id.film_duration);
        modifyListLayout = view.findViewById(R.id.film_modify_list);
        addToListLayout = view.findViewById(R.id.film_add_to_list);
        removeFromListLayout = view.findViewById(R.id.film_remove_from_list);
        rateLayout = view.findViewById(R.id.film_rate);
        description = view.findViewById(R.id.film_description);
        suggestions = view.findViewById(R.id.film_suggestions);

        backButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
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

        suggestions.setAdapter(new RecyclerViewFilmCategoryAdapter(suggestionsList, getActivity()));
        suggestions.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.film_back:
                getActivity().onBackPressed();
                break;
            case R.id.film_play:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myFilm.getPlayLink()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
                break;
            case R.id.film_modify_list:
                if (FirebaseData.getCurrentUser().getList().contains(myFilm.getTitle())){
                    removeFilmFromList();
                }else{
                    saveFilmToList();
                }
                break;
            case R.id.film_rate:
                Toast.makeText(getActivity(), "Rate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getFilm(){
        for (Film aux : FirebaseData.getFilmList()){
            if (aux.getTitle().equals(getArguments().get("Film"))){
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
                Toast.makeText(getActivity(), "Added to list", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Removed from list", Toast.LENGTH_SHORT).show();
            }
        });

        addToListLayout.setVisibility(View.VISIBLE);
        removeFromListLayout.setVisibility(View.INVISIBLE);
    }
}
