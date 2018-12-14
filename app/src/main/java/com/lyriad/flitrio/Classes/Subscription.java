package com.lyriad.flitrio.Classes;

public class Subscription {

    private long id;
    private String title;
    private String resolution;
    private int simultaneousDevices;

    public Subscription(String title, String resolution, int simultaneousDevices){
        super();
        this.title = title;
        this.resolution = resolution;
        this.simultaneousDevices = simultaneousDevices;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getSimultaneousDevices() {
        return simultaneousDevices;
    }

    public void setSimultaneousDevices(int simultaneousDevices) {
        this.simultaneousDevices = simultaneousDevices;
    }
}
