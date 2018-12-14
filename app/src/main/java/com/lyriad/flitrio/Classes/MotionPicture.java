package com.lyriad.flitrio.Classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class MotionPicture {

    private long id;
    private String title;
    private int duration; //in minutes
    private ArrayList<String> genres; //first genre is main genre
    private LocalDate releaseDate;
    private float score;
    private byte[] image;
    private String countryOfOrigin;

    public MotionPicture(String title, int duration, ArrayList<String> genres,
                         LocalDate releaseDate, float score, String countryOfOrigin){
        super();
        this.title = title;
        this.duration = duration;
        if(genres == null){
            this.genres = new ArrayList<>();
        }else{
            this.genres = genres;
        }
        this.releaseDate = releaseDate;
        this.score = score;
        this.countryOfOrigin = countryOfOrigin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
