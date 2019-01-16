package com.lyriad.flitrio.Classes;

import android.os.Build;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public final class FirebaseData {

    private static final String TAG = "FirebaseData";

    private static User currentUser = new User();
    private static List<Film> filmList = new ArrayList<>();
    private static List<TVSeries> TVSeriesList = new ArrayList<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void resetUser(){currentUser = new User();}

    public static List<Film> getFilmList() {
        return filmList;
    }

    public static List<TVSeries> getTVSeriesList() {
        return TVSeriesList;
    }

    public static void loadMedia(final FirebaseFirestore fireDatabase){

        filmList.clear();
        TVSeriesList.clear();

        fireDatabase.collection(StaticData.getFilmCollection()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                filmList.add(new Film(
                                        document.get("Title").toString(),
                                        document.get("Summary").toString(),
                                        document.get("Play").toString(),
                                        Integer.parseInt(document.get("Duration").toString()),
                                        (ArrayList<String>) document.get("Cast"),
                                        document.get("Wallpaper").toString(),
                                        document.get("Poster").toString(),
                                        document.get("Country of Origin").toString(),
                                        (ArrayList<String>) document.get("Genres"),
                                        LocalDate.parse(document.get("Release Date").toString(), StaticData.getDateFormat()),
                                        Float.parseFloat(document.get("Score").toString()))
                                    );
                            }
                        }
                    }
                });

        fireDatabase.collection(StaticData.getTvSeriesCollection()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        List<Season> seasons = new ArrayList<>();
                        String seasonTitle = "", seasonImage = "";
                        LocalDate seasonStartDate = null, seasonEndDate = null;
                        String episodeTitle = "", episodeSummary = "", episodePlay = "";
                        int episodeDuration = 0;
                        Map<String, Object> seasonsMap = (HashMap<String, Object>) document.get("Seasons");
                        for (Map.Entry<String, Object> entrySeason : seasonsMap.entrySet()) {
                            Map<String, Object> seasonMap = (HashMap<String, Object>) entrySeason.getValue();
                            List<Episode> episodes = new ArrayList<>();
                            for (Map.Entry<String, Object> season : seasonMap.entrySet()){
                                switch (season.getKey()){
                                    case "Title":
                                        seasonTitle = season.getValue().toString();
                                        break;
                                    case "Season Image":
                                        seasonImage = season.getValue().toString();
                                        break;
                                    case "Start Date":
                                        seasonStartDate = LocalDate.parse(season.getValue().toString(), StaticData.getDateFormat());
                                        break;
                                    case "End Date":
                                        seasonEndDate = LocalDate.parse(season.getValue().toString(), StaticData.getDateFormat());
                                        break;
                                    case "Episodes":
                                        Map<String, Object> episodesMap = (HashMap<String, Object>) season.getValue();
                                        for (Map.Entry<String, Object> entryEpisode : episodesMap.entrySet()) {
                                            Map<String, Object> episodeMap = (HashMap<String, Object>) entryEpisode.getValue();
                                            for (Map.Entry<String, Object> episode : episodeMap.entrySet()){
                                                switch (episode.getKey()){
                                                    case "Title":
                                                        episodeTitle = episode.getValue().toString();
                                                        break;
                                                    case "Summary":
                                                        episodeSummary = episode.getValue().toString();
                                                        break;
                                                    case "Duration":
                                                        episodeDuration = Integer.parseInt(episode.getValue().toString());
                                                        break;
                                                    case "Play":
                                                        episodePlay = episode.getValue().toString();
                                                        break;
                                                }
                                            }
                                            episodes.add(new Episode(episodeTitle, episodeSummary, episodePlay, episodeDuration));
                                        }
                                        break;
                                }
                            }
                            seasons.add(new Season(seasonTitle, seasonStartDate, seasonEndDate, seasonImage, episodes));
                        }

                        TVSeriesList.add(new TVSeries(
                                document.get("Title").toString(),
                                document.get("Genre").toString(),
                                document.get("Country of Origin").toString(),
                                document.get("Language").toString(),
                                document.get("Title Card").toString(),
                                document.get("Wallpaper").toString(),
                                Integer.parseInt(document.get("Running Time").toString()),
                                Float.parseFloat(document.get("Score").toString()),
                                document.get("Summary").toString(),
                                seasons)
                        );
                    }
                }
            }
        });
        Collections.sort(filmList);
        Collections.sort(TVSeriesList);
    }
}
