package com.aleat0r.weather.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.aleat0r.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Aleksandr Kovalenko on 14.05.2016.
 */
public class Utils {
    private static String getDateFromMillis(Context context, int format, long millis) {
        String date = new SimpleDateFormat(context.getString(format)).format(new Date(millis));
        return date;
    }

    public static String getTimeFromUnix(Context context, long unixDate) {
        String time = getDateFromMillis(context, R.string.time_format, unixDate * 1000L);
        return time;
    }

    public static String getForecastDateFromUnix(Context context, long unixDate) {
        String date = getDateFromMillis(context, R.string.forecast_date_format, unixDate * 1000L);
        return date;
    }

    public static String getLastUpdateDateFromMillis(Context context, long millis) {
        String date = getDateFromMillis(context, R.string.last_update_date_format, millis);
        return date;
    }


    public static void showMessage(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        return progressDialog;
    }
}
