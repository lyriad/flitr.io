package com.lyriad.flitrio.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TVSeriesFragment extends Fragment implements View.OnClickListener {

    private TVSeries tvShow;
    private List<Season> seasons = new ArrayList<>();

    TextView title, releaseYear, genre, seasonCount, description;
    ImageView backButton, wallpaper;
    RecyclerView seasonRecyclerView, episodeRecyclerView;
    RecyclerViewSeasonAdapter seasonAdapter;
    LinearLayout rateLayout, addToListLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTVShow();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvseries, container, false);

        seasonRecyclerView = view.findViewById(R.id.tv_series_seasons);
        episodeRecyclerView = view.findViewById(R.id.tv_series_episodes);
        backButton = view.findViewById(R.id.tv_series_back);
        wallpaper = view.findViewById(R.id.tv_series_wallpaper);
        title = view.findViewById(R.id.tv_series_title);
        releaseYear = view.findViewById(R.id.tv_series_release_year);
        genre = view.findViewById(R.id.tv_series_genre);
        seasonCount = view.findViewById(R.id.tv_series_season_count);
        addToListLayout = view.findViewById(R.id.tv_series_add_to_list);
        rateLayout = view.findViewById(R.id.tv_series_rate);
        description = view.findViewById(R.id.tv_series_description);

        backButton.setOnClickListener(this);
        addToListLayout.setOnClickListener(this);
        rateLayout.setOnClickListener(this);

        episodeRecyclerView.setAdapter(new RecyclerViewEpisodeAdapter(seasons.get(0).getEpisodes()));
        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        seasonAdapter = new RecyclerViewSeasonAdapter(seasons, getActivity());
        seasonAdapter.setOnItemClickListener(new RecyclerViewSeasonAdapter.OnItemClickListener() {
            public void onItemClick(int position) {
                episodeRecyclerView.swapAdapter(new RecyclerViewEpisodeAdapter(seasons.get(position).getEpisodes()), true);
            }
        });
        seasonRecyclerView.setAdapter(seasonAdapter);
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Glide.with(this).load(tvShow.getWallpaperUrl()).into(wallpaper);
        title.setText(tvShow.getTitle());
        releaseYear.setText(String.valueOf(tvShow.getSeasons().get(0).getStartDate().getYear()));
        genre.setText(tvShow.getGenre());
        seasonCount.setText(String.valueOf(tvShow.getSeasons().size()) + " Seasons");
        description.setText(tvShow.getSummary());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_series_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_series_add_to_list:
                Toast.makeText(getActivity(), "Added to list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_series_rate:
                Toast.makeText(getActivity(), "Rate", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loadTVShow(){

        for (TVSeries aux : FirebaseData.getTVSeriesList()){
            if (aux.getTitle().equals(getArguments().get("TV Series"))){
                tvShow = aux;
                break;
            }
        }

        seasons = tvShow.getSeasons();
    }
}
