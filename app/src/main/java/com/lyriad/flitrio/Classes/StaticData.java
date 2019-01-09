package com.lyriad.flitrio.Classes;

import android.os.Build;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;

public final class StaticData {

    private static final String userCollection = "User";
    private static final String filmCollection = "Films";
    private static final String tvSeriesCollection = "TV Series";
    private static final String profilePictureFolder = "Profile Pictures";
    private static final String emptyPicture = "https://firebasestorage.googleapis.com/v0/b/flitrio.appspot.com/o/Profile%20Pictures%2Fempty_profile_picture.png?alt=media&token=4de925ab-d22c-4db7-b9c8-e0bff7a2e8ea";

    private static String subscriptions[] = {"<Select subscription plan>",
            "Basic plan", "Standard plan", "Premium plan"};

    private static List<String> countries = new ArrayList<>();

    private static String genres[] = {"Action", "Adventure", "Comedy", "Crime", "Drama", "Historical",
            "Horror", "Musical", "Science Fiction", "War", "Western", "Biographical", "Mystery",
            "Disaster", "Fantasy", "Romance", "Sports", "Superhero", "Supernatural", "Thriller",
            "Zombie", "Animated", "Documentary", "Erotic", "Silent"};

    public static String getUserCollection() {
        return userCollection;
    }

    public static String getFilmCollection() {
        return filmCollection;
    }

    public static String getTvSeriesCollection() {
        return tvSeriesCollection;
    }

    public static String getProfilePictureFolder() {
        return profilePictureFolder;
    }

    public static String getEmptyPicture() {
        return emptyPicture;
    }

    public static List<String> getCountries() {
        return countries;
    }

    public static String[] getSubscriptions() {
        return subscriptions;
    }

    public static String[] getGenres() {
        return genres;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DateTimeFormatter getDateFormat(){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static void loadCountries(){
        countries.clear();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes){

            Locale locale = new Locale("", countryCode);
            String name = locale.getDisplayCountry();
            countries.add(name);
        }
        Collections.sort(countries);
    }
}
