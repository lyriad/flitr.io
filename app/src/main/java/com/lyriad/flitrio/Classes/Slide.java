package com.lyriad.flitrio.Classes;

public class Slide {

    private String image;
    private String title;

    public Slide(String title, String image) {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
