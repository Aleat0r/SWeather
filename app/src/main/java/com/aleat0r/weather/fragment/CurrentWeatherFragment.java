package com.aleat0r.weather.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleat0r.weather.R;
import com.aleat0r.weather.activity.MainActivity;
import com.aleat0r.weather.bus.event.ErrorEventCurrentWeather;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.pojo.weather.current.CurrentWeatherData;
import com.aleat0r.weather.util.Utils;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by Aleksandr Kovalenko on 14.05.2016.
 */
public class CurrentWeatherFragment extends WeatherFragment implements View.OnClickListener {

    private ImageView mImgWeatherType;

    private TextView mTvWeatherDescription;
    private TextView mTvTemperature;
    private TextView mTvWind;
    private TextView mTvHumidity;
    private TextView mTvCloudiness;
    private TextView mTvSunrise;
    private TextView mTvSunset;
    private TextView mTvPressure;
    private TextView mTvLastUpdate;

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mToolbar = ((MainActivity) getActivity()).getToolbar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCoordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.fragment_current_weather, container, false);
        initViews(mCoordinatorLayout);
        updateWeatherInfo();
        return mCoordinatorLayout;
    }

    private void updateWeatherInfo() {
        mProgressDialog.show();
        super.getWeatherByCity(ApiConstants.WEATHER_CURRENT, ApiConstants.DEFAULT_CITY);
    }

    private void initViews(View view) {
        mProgressDialog = Utils.createProgressDialog(getActivity());

        mImgWeatherType = (ImageView) view.findViewById(R.id.img_weather_type);
        mTvWeatherDescription = (TextView) view.findViewById(R.id.tv_weather_description);
        mTvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        mTvWind = (TextView) view.findViewById(R.id.tv_wind);
        mTvHumidity = (TextView) view.findViewById(R.id.tv_humidity);
        mTvCloudiness = (TextView) view.findViewById(R.id.tv_cloudiness);
        mTvSunrise = (TextView) view.findViewById(R.id.tv_sunrise);
        mTvSunset = (TextView) view.findViewById(R.id.tv_sunset);
        mTvPressure = (TextView) view.findViewById(R.id.tv_pressure);
        mTvLastUpdate = (TextView) view.findViewById(R.id.tv_last_update);

        mTvWind.setOnClickListener(this);
        mTvHumidity.setOnClickListener(this);
        mTvCloudiness.setOnClickListener(this);
        mTvSunrise.setOnClickListener(this);
        mTvSunset.setOnClickListener(this);
        mTvPressure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wind:
                Utils.showMessage(mCoordinatorLayout, R.string.description_tv_wind);
                break;
            case R.id.tv_humidity:
                Utils.showMessage(mCoordinatorLayout, R.string.description_tv_humidity);
                break;
            case R.id.tv_cloudiness:
                Utils.showMessage(mCoordinatorLayout, R.string.description_tv_cloudiness);
                break;
            case R.id.tv_sunrise:
                Utils.showMessage(mCoordinatorLayout, (R.string.description_tv_sunrise));
                break;
            case R.id.tv_sunset:
                Utils.showMessage(mCoordinatorLayout, R.string.description_tv_sunset);
                break;
            case R.id.tv_pressure:
                Utils.showMessage(mCoordinatorLayout, R.string.description_tv_pressure);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onWeatherDataEvent(CurrentWeatherData currentWeatherData) {
        setWeatherData(currentWeatherData);
        setDateLastUpdate(currentWeatherData.getUpdateDate());
        mProgressDialog.dismiss();
        Utils.showMessage(mCoordinatorLayout, R.string.update_success);
    }

    @Subscribe
    public void onErrorEvent(ErrorEventCurrentWeather errorEventCurrentWeather) {
        mProgressDialog.dismiss();
        Utils.showMessage(mCoordinatorLayout, R.string.error_update);
    }

    private void setWeatherData(CurrentWeatherData weatherData) {
        String weatherTypeIconUrl = ApiConstants.ICON_WEATHER_TYPE_URL + weatherData.getWeather().get(0).getIcon() + getString(R.string.image_format);
        Picasso.with(getActivity()).load(weatherTypeIconUrl).placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error_loading).into(mImgWeatherType);
        mTvWeatherDescription.setText(weatherData.getWeather().get(0).getDescription());
        mTvTemperature.setText(String.valueOf(weatherData.getMain().getTemp() + getString(R.string.empty_place) + getString(R.string.degrees_celsius)));
        mTvWind.setText(String.valueOf(weatherData.getWind().getSpeed()) + getString(R.string.empty_place) + getString(R.string.metric_speed));
        mTvHumidity.setText(String.valueOf(weatherData.getMain().getHumidity()) + getString(R.string.empty_place) + getString(R.string.percent));
        mTvCloudiness.setText(String.valueOf(weatherData.getClouds().getAll()) + getString(R.string.empty_place) + getString(R.string.percent));
        mTvSunrise.setText(Utils.getTimeFromUnix(getActivity(), weatherData.getSys().getSunrise()));
        mTvSunset.setText(Utils.getTimeFromUnix(getActivity(), weatherData.getSys().getSunset()));
        mTvPressure.setText(String.valueOf(weatherData.getMain().getPressure()) + getString(R.string.empty_place) + getString(R.string.hectopascal));

        mToolbar.setTitle(weatherData.getName());
    }

    private void setDateLastUpdate(Date date) {
        mTvLastUpdate.setText(Utils.convertDateToString(getActivity(), date));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
                updateWeatherInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


