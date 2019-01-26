package com.lyriad.flitrio.Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Season implements Comparable<Season>{

    private String title, seasonImage;
    private LocalDate startDate, endDate;
    private List<Episode> episodes;

    public Season(){
        super();
    }

    public Season(String title, LocalDate startDate, LocalDate endDate,
                  String seasonImage, List<Episode> episodes){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seasonImage = seasonImage;
        if(episodes == null) this.episodes = new ArrayList<>(); else this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSeasonImage() {
        return seasonImage;
    }

    public void setSeasonImage(String seasonImage) {
        this.seasonImage = seasonImage;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public int compareTo(Season o) {
        return title.compareTo(o.getTitle());
    }
}
