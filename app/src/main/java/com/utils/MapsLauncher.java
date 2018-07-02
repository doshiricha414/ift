package com.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MapsLauncher {

    public enum TRAVELLING_MODE {BICYCLE, DRIVING, WALKING}

    private static final String geo = "geo:", nav = "google.navigation:", stv = "google.streetview:cbll=";

    public static void navigateToLocation(Context context, double latitude, double longitude) {
        String streetview = stv + latitude + "," + longitude;
        // Create a Uri from an intent string. Use the result to create an Intent.
        //Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
        Uri gmmIntentUri = Uri.parse(streetview);

        startMapIntent(context, gmmIntentUri);

    }

    public static void displayMapLocation(Context context, double latitude, double longitude, int mapZoomLevel, String address, String label) {
        String geo = MapsLauncher.geo + latitude + "," + longitude;
        String mapZoomLevelUri = "z=" + ((mapZoomLevel < 0) ? 0 : (mapZoomLevel > 21 ? 21 : mapZoomLevel));
        String query = "q=" + address + (label == null ? "" : "(" + label + ")");
        Uri gmmIntentUri = Uri.parse(geo + "?" + mapZoomLevelUri + "&" + query);

        startMapIntent(context, gmmIntentUri);
    }

    public static void mapLocationSearch(Context context, double nearLatitude, double nearLongitude, String address, String label) {
        // It will search nearby (latitude,longitude) area that you provided; you must pass (0,0) params for (lat/lon) to search everywhere on map
        String geo = MapsLauncher.geo + nearLatitude + "," + nearLongitude;
        String query = "q=" + address + (label == null ? "" : "(" + label + ")");
        Uri gmmIntentUri = Uri.parse(geo + "?" + query);

        startMapIntent(context, gmmIntentUri);
    }

    public static void navigateTurnByTurn(Context context, String address) {
        String query = "q=" + address;
        Uri gmmIntentUri = Uri.parse(nav + query);

        startMapIntent(context, gmmIntentUri);
    }

    public static void navigateTurnByTurn(Context context, double latitude, double longitude) {
        String query = "q=" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(nav + query);

        startMapIntent(context, gmmIntentUri);
    }

    public static void navigateTurnByTurn(Context context, String address, TRAVELLING_MODE travelling_mode) {
        String query = "q=" + address;
        Uri gmmIntentUri = Uri.parse(nav + query + "&" + getMode(travelling_mode));

        startMapIntent(context, gmmIntentUri);
    }

    public static void navigateTurnByTurn(Context context, double latitude, double longitude, TRAVELLING_MODE travelling_mode) {
        String query = "q=" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(nav + query + "&" + getMode(travelling_mode));

        startMapIntent(context, gmmIntentUri);
    }

    // generate maps intent

    private static void startMapIntent(Context context, Uri gmmIntentUri) {
        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    // other methods

    private static String getMode(TRAVELLING_MODE travelling_mode) {
        String mode = "mode=";
        switch (travelling_mode) {
            case BICYCLE:
                return mode + "b";
            case DRIVING:
                return mode + "d";
            default:
                return mode + "w";
        }
    }
}
