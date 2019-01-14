package com.lyriad.flitrio.Adapters.Models;

import java.util.List;
import com.lyriad.flitrio.Classes.Film;

public class FilmCategoryModel {

    private String categoryTitle;
    private List<Film> categoryFilms;

    public FilmCategoryModel(String categoryTitle, List<Film> categoryFilms) {
        this.categoryTitle = categoryTitle;
        this.categoryFilms = categoryFilms;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<Film> getCategoryFilms() {
        return categoryFilms;
    }

    public void setCategoryFilms(List<Film> categoryFilms) {
        this.categoryFilms = categoryFilms;
    }
}
