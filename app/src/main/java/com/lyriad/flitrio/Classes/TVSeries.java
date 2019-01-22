package com.lyriad.flitrio.Classes;

import java.util.List;
import java.util.ArrayList;

public class TVSeries implements Comparable<TVSeries>{

    private String title, genre, countryOfOrigin, language, titleCard, wallpaperUrl, summary;
    private float score;
    private List<Season> seasons;

    public TVSeries(String title, String genre, String countryOfOrigin, String language,
                    String titleCard, String wallpaperUrl, float score,
                    String summary, List<Season> seasons) {
        this.title = title;
        this.genre = genre;
        this.countryOfOrigin = countryOfOrigin;
        this.language = language;
        this.titleCard = titleCard;
        this.wallpaperUrl = wallpaperUrl;
        this.score = score;
        this.summary = summary;
        if (seasons == null) this.seasons = new ArrayList<>(); else this.seasons = seasons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitleCard() {
        return titleCard;
    }

    public void setTitleCard(String titleCard) {
        this.titleCard = titleCard;
    }

    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int compareTo(TVSeries o) {
        if(this.getScore() > o.getScore()){
            return 1;
        }
        return 0;
    }
}
