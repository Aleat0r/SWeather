package com.aleat0r.weather.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleat0r.weather.R;
import com.aleat0r.weather.activity.MainActivity;
import com.aleat0r.weather.adapter.ForecastRecyclerAdapter;
import com.aleat0r.weather.bus.event.ErrorEvent;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.pojo.weather.forecast.ForecastWeatherData;
import com.aleat0r.weather.util.Utils;
import com.squareup.otto.Subscribe;

import java.util.Date;

/**
 * Created by Aleksandr Kovalenko on 16.05.2016.
 */
public class ForecastWeatherFragment extends WeatherFragment {

    private RecyclerView mRvForecast;
    private TextView mTvLastUpdate;

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mToolbar = ((MainActivity) getActivity()).getToolbar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCoordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        initViews(mCoordinatorLayout);
        updateWeatherInfo();
        return mCoordinatorLayout;
    }

    private void initViews(View view) {
        mTvLastUpdate = (TextView) view.findViewById(R.id.tv_last_update);

        mRvForecast = (RecyclerView) view.findViewById(R.id.rv_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvForecast.setLayoutManager(layoutManager);
    }

    private void updateWeatherInfo() {
        super.getWeatherByCity(ApiConstants.WEATHER_FORECAST, ApiConstants.DEFAULT_CITY);
    }

    @Subscribe
    public void onWeatherDataEvent(ForecastWeatherData forecastData) {
        setWeatherData(forecastData);
        setDateLastUpdate(forecastData.getUpdateDate());
        Utils.showMessage(mCoordinatorLayout, R.string.update_success);
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Utils.showMessage(mCoordinatorLayout, R.string.error_update);
    }

    private void setWeatherData(ForecastWeatherData forecastData) {
        mToolbar.setTitle(forecastData.getCity().getName());
        ForecastRecyclerAdapter forecastRecyclerAdapter = new ForecastRecyclerAdapter(getActivity(), forecastData.getList());
        mRvForecast.setAdapter(forecastRecyclerAdapter);
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
