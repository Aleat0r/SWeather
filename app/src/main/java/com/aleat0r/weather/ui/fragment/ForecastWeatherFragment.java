package com.aleat0r.weather.ui.fragment;

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
import com.aleat0r.weather.ui.activity.MainActivity;
import com.aleat0r.weather.ui.adapter.ForecastRecyclerAdapter;
import com.aleat0r.weather.bus.event.ErrorEventForecastWeather;
import com.aleat0r.weather.bus.event.UpdateEvent;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.realm.weather.forecast.City;
import com.aleat0r.weather.realm.weather.forecast.ForecastWeatherData;
import com.aleat0r.weather.realm.weather.forecast.WeatherList;
import com.aleat0r.weather.util.PreferencesUtils;
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

    private CoordinatorLayout mRootView;
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
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_forecast_weather, container, false);

        initViews(mRootView);

        mRealm = Realm.getDefaultInstance();
        mRealmResults = mRealm.where(ForecastWeatherData.class).findAll();

        if (mRealmResults.size() == 0) {
            updateWeatherInfo();
        } else {
            setWeatherData(mRealmResults.get(0));
        }

        return mRootView;
    }

    private void initViews(View view) {
        mProgressDialog = Utils.createProgressDialog(getActivity());

        mTvLastUpdate = (TextView) view.findViewById(R.id.tv_last_update);

        mRvForecast = (RecyclerView) view.findViewById(R.id.rv_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvForecast.setLayoutManager(layoutManager);
    }

    private void updateWeatherInfo() {
        mProgressDialog.show();
        super.getWeatherByCoordinates(ApiConstants.WEATHER_FORECAST, PreferencesUtils.getLocationLongitude(getActivity()),
                PreferencesUtils.getLocationLatitude(getActivity()));
    }

    @Subscribe
    public void updateWeatherInfo(UpdateEvent updateEvent) {
        updateWeatherInfo();
    }

    @Subscribe
    public void onWeatherDataEvent(ForecastWeatherData forecastData) {
        setWeatherData(forecastData);
        deleteOldWeatherCurrentData();
        setInRealm(forecastData);
        mProgressDialog.dismiss();
        Utils.showMessage(mRootView, getString(R.string.update_success));
    }

    @Subscribe
    public void onErrorEvent(ErrorEventForecastWeather errorEventForecastWeather) {
        mProgressDialog.dismiss();
        Utils.showMessage(mRootView, getString(R.string.error_update));
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
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
