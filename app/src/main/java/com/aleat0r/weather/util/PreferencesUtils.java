package com.aleat0r.weather.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aleat0r.weather.network.ApiConstants;

/**
 * Created by Aleksandr Kovalenko on 20.05.2016.
 */
public class PreferencesUtils {

    public static void setLocationName(Context context, String locationName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.LOCATION_NAME_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LOCATION_NAME_PREFERENCE, locationName);
        editor.commit();
    }

    public static String getLocationName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.LOCATION_NAME_PREFERENCE, Context.MODE_PRIVATE);
        String locationName = sharedPreferences.getString(Constants.LOCATION_NAME_PREFERENCE, Constants.DEFAULT_CITY);
        return locationName;
    }

}
