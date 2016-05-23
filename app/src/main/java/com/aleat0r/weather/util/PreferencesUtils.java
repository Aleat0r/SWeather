package com.aleat0r.weather.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aleksandr Kovalenko on 20.05.2016.
 */
public class PreferencesUtils {
    public static final String DEFAULT_CITY = "Zaporizhzhya";
    public static final float DEFAULT_CITY_LONGITUDE = 35.1626f;
    public static final float DEFAULT_CITY_LATITUDE = 47.8289f;

    public static final String LOCATION_NAME_PREFERENCE = "location_name";
    public static final String LOCATION_PREFERENCE = "location";
    public static final String LOCATION_LONGITUDE = "longitude";
    public static final String LOCATION_LATITUDE = "latitude";

    public static void setLocationName(Context context, String locationName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION_NAME_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATION_NAME_PREFERENCE, locationName);
        editor.commit();
    }

    public static String getLocationName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION_NAME_PREFERENCE, Context.MODE_PRIVATE);
        String locationName = sharedPreferences.getString(LOCATION_NAME_PREFERENCE, DEFAULT_CITY);
        return locationName;
    }


    public static void setLocation(Context context, double longitude, double latitude) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(LOCATION_LONGITUDE, (float) longitude);
        editor.putFloat(LOCATION_LATITUDE, (float) latitude);
        editor.commit();
    }

    public static double getLocationLongitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        double longitude = (double) sharedPreferences.getFloat(LOCATION_LONGITUDE, DEFAULT_CITY_LONGITUDE);
        return longitude;
    }

    public static double getLocationLatitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        double latitude = (double) sharedPreferences.getFloat(LOCATION_LATITUDE, DEFAULT_CITY_LATITUDE);
        return latitude;
    }

}
