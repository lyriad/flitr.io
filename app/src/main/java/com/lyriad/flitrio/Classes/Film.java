package com.lyriad.flitrio.Classes;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Film extends Episode implements Comparable<Film>{

    private String wallpaperUrl, posterUrl, countryOfOrigin;
    private List<String> genres; //first genre is main genre
    private LocalDate releaseDate;
    private float score;
    private List<String> mainCast; //first genre is main genre

    public Film(String title, String summary, String playLink, String duration,
                List<String> mainCast, String wallpaperUrl, String posterUrl,
                String countryOfOrigin, List<String> genres, LocalDate releaseDate, float score) {
        super(title, summary, playLink, duration);
        this.wallpaperUrl = wallpaperUrl;
        this.posterUrl = posterUrl;
        this.countryOfOrigin = countryOfOrigin;
        this.releaseDate = releaseDate;
        this.score = score;
        if (genres == null) this.genres = new ArrayList<>(); else this.genres = genres;
        if (mainCast == null) this.mainCast = new ArrayList<>(); else this.mainCast = mainCast;
    }

    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    @Override
    public int compareTo(Film o) {
        if(this.getScore() > o.getScore()){
            return 1;
        }
        return 0;
    }
}
