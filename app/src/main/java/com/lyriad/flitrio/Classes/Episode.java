package com.lyriad.flitrio.Classes;

public class Episode {
    private String title, summary, playLink;
    private int duration; //in minutes

    public Episode(String title, String summary, String playLink, int duration) {
        this.title = title;
        this.summary = summary;
        this.playLink = playLink;
        this.duration = duration;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPlayLink() {
        return playLink;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }
}
