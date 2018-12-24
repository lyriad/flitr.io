package com.lyriad.flitrio.Classes;

public final class StaticData {

    private static final String userCollection = "User";
    private static final String firebaseStorage = "gs://flitrio.appspot.com";
    private static final String profilePictureFolder = "ProfilePicture";

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

    public static String getFirebaseStorage() {
        return firebaseStorage;
    }

    public static String getProfilePictureFolder() {
        return profilePictureFolder;
    }

    public static String[] getCountries() {
        return countries;
    }

    public static String[] getSubscriptions() {
        return subscriptions;
    }
}
