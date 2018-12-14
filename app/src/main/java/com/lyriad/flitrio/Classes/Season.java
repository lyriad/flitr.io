package com.lyriad.flitrio.Classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Season {

    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private TVSeries parentTVSeries;
    private byte[] seasonImage;
    private ArrayList<MotionPicture> episodes;

    public Season(String title, LocalDate startDate, LocalDate endDate, TVSeries parentTVSeries,
                  ArrayList<MotionPicture> episodes){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.parentTVSeries = parentTVSeries;
        if(episodes == null){
            this.episodes = new ArrayList<>();
        }else{
            this.episodes = episodes;
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

    public TVSeries getParentTVSeries() {
        return parentTVSeries;
    }

    public void setParentTVSeries(TVSeries parentTVSeries) {
        this.parentTVSeries = parentTVSeries;
    }

    public byte[] getSeasonImage() {
        return seasonImage;
    }

    public void setSeasonImage(byte[] seasonImage) {
        this.seasonImage = seasonImage;
    }

    public ArrayList<MotionPicture> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<MotionPicture> episodes) {
        this.episodes = episodes;
    }

}
