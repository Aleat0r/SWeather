package com.aleat0r.weather.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aleat0r.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aleksandr Kovalenko on 14.05.2016.
 */
public class Utils {
    private static String getDateFromMillis(Context context, int format, long millis) {
        String date = new SimpleDateFormat(context.getString(format), Locale.ENGLISH).format(new Date(millis));
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


    public static void showMessage(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        TextView snackbarTextView = (TextView)snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarTextView.setMaxLines(Constants.SNACK_BAR_MAX_LINES);
        snackbar.show();
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        return progressDialog;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
