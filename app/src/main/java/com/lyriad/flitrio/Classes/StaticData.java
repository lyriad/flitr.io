package com.lyriad.flitrio.Classes;

import android.net.Uri;

public final class StaticData {

    private static final String userCollection = "User";
    private static final String profilePictureFolder = "Profile Pictures";
    private static final String emptyPicture = "https://firebasestorage.googleapis.com/v0/b/flitrio.appspot.com/o/Profile%20Pictures%2Fempty_profile_picture.png?alt=media&token=4de925ab-d22c-4db7-b9c8-e0bff7a2e8ea";

    private static String subscriptions[] = {"<Select subscription plan>",
            "Basic plan", "Standard plan", "Premium plan"};

    private static String countries[] = {"<Select country of origin>",
            "Antigua and Barbuda", "Anguilla", "Aruba",
            "Bahamas", "Barbados", "Belize", "Bermuda", "Bonaire",
            "British Virgin Islands", "Canada", "Costa Rica",
            "Cuba", "Cayman Islands", "Clipperton Island", "Curacao",
            "Dominica", "Dominican Republic", "El Salvador", "Grenada",
            "Guatemala", "Greenland", "Guadeloupe", "Haiti", "Honduras",
            "Jamaica", "Martinique", "Mexico", "Monserrat", "Nicaragua",
            "Navassa Island", "Panama", "Puerto Rico", "Saint Lucia",
            "Saint Martin", "Sint Marteen", "Trinidad and Tobago",
            "Turks and Caicos Islands", "United States", "US Virgin Islands"};

    public static String getUserCollection() {
        return userCollection;
    }

    public static String getProfilePictureFolder() {
        return profilePictureFolder;
    }

    public static String getEmptyPicture() {
        return emptyPicture;
    }

    public static String[] getCountries() {
        return countries;
    }

    public static String[] getSubscriptions() {
        return subscriptions;
    }
}
