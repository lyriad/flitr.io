package com.lyriad.flitrio.Classes;

import java.util.ArrayList;

public class TVSeries {

    private long id;
    private String title;
    private String genre;
    private String countryOfOrigin;
    private String language;
    private int runningTime; //in minutes
    private byte[] titleCard;
    private float score;
    private ArrayList<Season> seasons;

    public TVSeries(String title, String genre, String countryOfOrigin, String language,
                    int runningTime, float score, ArrayList<Season> seasons){
        super();
        this.title = title;
        this.genre = genre;
        this.countryOfOrigin = countryOfOrigin;
        this.language = language;
        this.runningTime = runningTime;
        this.score = score;
        if(seasons == null){
            this.seasons = new ArrayList<>();
        }else{
            this.seasons = seasons;
        }
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getTitleCard() {
        return titleCard;
    }

    public void setTitleCard(byte[] titleCard) {
        this.titleCard = titleCard;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
