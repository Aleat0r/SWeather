package com.aleat0r.weather.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.aleat0r.weather.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Aleksandr Kovalenko on 14.05.2016.
 */
public class Utils {

    public static String getTimeFromUnix(Context context, long unixDate) {
        String time = new SimpleDateFormat(context.getString(R.string.time_format)).format(new Date(unixDate * 1000L));
        return time;
    }

    public static String getDateFromUnix(Context context, long unixDate){
        String date = new SimpleDateFormat(context.getString(R.string.forecast_date_format)).format(new Date(unixDate * 1000L));
        return date;
    }

    public static String convertDateToString(Context context, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.last_update_date_format));
        return dateFormat.format(date);
    }

    public static void showMessage(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
