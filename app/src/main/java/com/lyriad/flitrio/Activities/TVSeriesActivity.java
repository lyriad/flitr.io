package com.lyriad.flitrio.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.lyriad.flitrio.Adapters.RecyclerViewEpisodeAdapter;
import com.lyriad.flitrio.Adapters.RecyclerViewSeasonAdapter;
import com.lyriad.flitrio.Classes.FirebaseData;
import com.lyriad.flitrio.Classes.Season;
import com.lyriad.flitrio.Classes.TVSeries;
import com.lyriad.flitrio.R;

import java.util.ArrayList;
import java.util.List;

public class TVSeriesActivity extends AppCompatActivity implements View.OnClickListener{

    private TVSeries tvShow;
    private List<Season> seasons = new ArrayList<>();
    private Intent intent;

    TextView title, releaseYear, genre, seasonCount, description;
    ImageView backButton, wallpaper;
    RecyclerView seasonRecyclerView, episodeRecyclerView;
    RecyclerViewSeasonAdapter seasonAdapter;
    LinearLayout rateLayout, addToListLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries);
        intent = getIntent();
        loadSeasons();

        seasonRecyclerView = findViewById(R.id.tv_series_seasons);
        episodeRecyclerView = findViewById(R.id.tv_series_episodes);
        backButton = findViewById(R.id.tv_series_back);
        wallpaper = findViewById(R.id.tv_series_wallpaper);
        title = findViewById(R.id.tv_series_title);
        releaseYear = findViewById(R.id.tv_series_release_year);
        genre = findViewById(R.id.tv_series_genre);
        seasonCount = findViewById(R.id.tv_series_season_count);
        addToListLayout = findViewById(R.id.tv_series_add_to_list);
        rateLayout = findViewById(R.id.tv_series_rate);
        description = findViewById(R.id.tv_series_description);

        backButton.setOnClickListener(this);
        addToListLayout.setOnClickListener(this);
        rateLayout.setOnClickListener(this);

        episodeRecyclerView.setAdapter(new RecyclerViewEpisodeAdapter(seasons.get(0).getEpisodes()));
        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        seasonAdapter = new RecyclerViewSeasonAdapter(seasons, this);
        seasonAdapter.setOnItemClickListener(new RecyclerViewSeasonAdapter.OnItemClickListener() {
            public void onItemClick(int position) {
                episodeRecyclerView.swapAdapter(new RecyclerViewEpisodeAdapter(seasons.get(position).getEpisodes()), true);
            }
        });
        seasonRecyclerView.setAdapter(seasonAdapter);
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Glide.with(this).load(tvShow.getWallpaperUrl()).into(wallpaper);
        title.setText(tvShow.getTitle());
        releaseYear.setText(String.valueOf(tvShow.getSeasons().get(0).getStartDate().getYear()));
        genre.setText(tvShow.getGenre());
        seasonCount.setText(String.valueOf(tvShow.getSeasons().size()) + " Seasons");
        description.setText(tvShow.getSummary());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_series_back:
                finish();
                break;
            case R.id.tv_series_add_to_list:
                Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_series_rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loadSeasons(){

        for (TVSeries aux : FirebaseData.getTVSeriesList()){
            if (aux.getTitle().equals(intent.getStringExtra("TV Series"))){
                tvShow = aux;
                break;
            }
        }

        seasons = tvShow.getSeasons();
    }
}
