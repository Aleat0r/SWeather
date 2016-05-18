package com.aleat0r.weather.fragment;

import android.app.ProgressDialog;
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
import com.aleat0r.weather.bus.event.ErrorEventForecastWeather;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.realm.weather.forecast.City;
import com.aleat0r.weather.realm.weather.forecast.ForecastWeatherData;
import com.aleat0r.weather.realm.weather.forecast.WeatherList;
import com.aleat0r.weather.util.Utils;
import com.squareup.otto.Subscribe;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Aleksandr Kovalenko on 16.05.2016.
 */
public class ForecastWeatherFragment extends WeatherFragment {

    private RecyclerView mRvForecast;
    private TextView mTvLastUpdate;

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    private ProgressDialog mProgressDialog;

    private Realm mRealm;
    private RealmResults<ForecastWeatherData> mRealmResults;

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

        mRealm = Realm.getDefaultInstance();
        setDataFromRealm();
        if (mRealmResults.size() == 0) {
            updateWeatherInfo();
        }

        return mCoordinatorLayout;
    }

    private void initViews(View view) {
        mProgressDialog = Utils.createProgressDialog(getActivity());

        mTvLastUpdate = (TextView) view.findViewById(R.id.tv_last_update);

        mRvForecast = (RecyclerView) view.findViewById(R.id.rv_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvForecast.setLayoutManager(layoutManager);
    }

    private void setDataFromRealm() {
        mRealmResults = mRealm.where(ForecastWeatherData.class).findAll();
        if (mRealmResults.size() > 0) {
            setWeatherData(mRealmResults.get(0));
        }
    }

    private void updateWeatherInfo() {
        mProgressDialog.show();
        super.getWeatherByCity(ApiConstants.WEATHER_FORECAST, ApiConstants.DEFAULT_CITY);
    }

    @Subscribe
    public void onWeatherDataEvent(ForecastWeatherData forecastData) {
        setWeatherData(forecastData);
        deleteOldWeatherCurrentData();
        setInRealm(forecastData);
        mProgressDialog.dismiss();
        Utils.showMessage(mCoordinatorLayout, R.string.update_success);
    }

    @Subscribe
    public void onErrorEvent(ErrorEventForecastWeather errorEventForecastWeather) {
        mProgressDialog.dismiss();
        Utils.showMessage(mCoordinatorLayout, R.string.error_update);
    }

    private void setInRealm(ForecastWeatherData forecastData) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(forecastData);
        mRealm.commitTransaction();
    }

    private void deleteOldWeatherCurrentData() {
        if (mRealmResults.size() > 0) {
            mRealm.beginTransaction();
            ForecastWeatherData oldForecastWeatherData = mRealmResults.get(0);
            City city = oldForecastWeatherData.getCity();
            city.getCoord().deleteFromRealm();
            city.deleteFromRealm();
            RealmList<WeatherList> weatherLists = oldForecastWeatherData.getWeatherList();
            for (WeatherList list : weatherLists) {
                list.getTemp().deleteFromRealm();
                list.getWeather().deleteAllFromRealm();
            }
            weatherLists.deleteAllFromRealm();
            oldForecastWeatherData.deleteFromRealm();
            mRealm.commitTransaction();
        }
    }

    private void setWeatherData(ForecastWeatherData forecastData) {
        mToolbar.setTitle(forecastData.getCity().getName());
        setDateLastUpdate(forecastData.getUpdateDate());

        ForecastRecyclerAdapter forecastRecyclerAdapter = new ForecastRecyclerAdapter(getActivity(), forecastData.getWeatherList());
        mRvForecast.setAdapter(forecastRecyclerAdapter);
    }

    private void setDateLastUpdate(long date) {
        mTvLastUpdate.setText(Utils.getLastUpdateDateFromMillis(getActivity(), date));
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

    @Override
    public void onStop() {
        super.onStop();
        mRealm.close();
    }
}
