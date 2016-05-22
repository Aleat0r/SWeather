package com.aleat0r.weather.ui.fragment;

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
import com.aleat0r.weather.ui.activity.MainActivity;
import com.aleat0r.weather.bus.event.ErrorEventCurrentWeather;
import com.aleat0r.weather.bus.event.UpdateEvent;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.realm.weather.current.CurrentWeatherData;
import com.aleat0r.weather.util.PreferencesUtils;
import com.aleat0r.weather.util.Utils;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;

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

    private CoordinatorLayout mRootView;
    private Toolbar mToolbar;

    private ProgressDialog mProgressDialog;

    private Realm mRealm;
    private RealmResults<CurrentWeatherData> mRealmResults;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mToolbar = ((MainActivity) getActivity()).getToolbar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_current_weather, container, false);

        initViews(mRootView);

        mRealm = Realm.getDefaultInstance();
        mRealmResults = mRealm.where(CurrentWeatherData.class).findAll();

        if (mRealmResults.size() == 0) {
            updateWeatherInfo();
        } else {
            setWeatherData(mRealmResults.get(0));
        }

        return mRootView;
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

    private void updateWeatherInfo() {
        mProgressDialog.show();
        super.getWeatherByCity(ApiConstants.WEATHER_CURRENT, PreferencesUtils.getLocationName(getActivity()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wind:
                Utils.showMessage(mRootView, getString(R.string.description_tv_wind));
                break;
            case R.id.tv_humidity:
                Utils.showMessage(mRootView, getString(R.string.description_tv_humidity));
                break;
            case R.id.tv_cloudiness:
                Utils.showMessage(mRootView, getString(R.string.description_tv_cloudiness));
                break;
            case R.id.tv_sunrise:
                Utils.showMessage(mRootView, getString(R.string.description_tv_sunrise));
                break;
            case R.id.tv_sunset:
                Utils.showMessage(mRootView, getString(R.string.description_tv_sunset));
                break;
            case R.id.tv_pressure:
                Utils.showMessage(mRootView, getString(R.string.description_tv_pressure));
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void updateWeatherInfo(UpdateEvent updateEvent) {
        updateWeatherInfo();
    }

    @Subscribe
    public void onWeatherDataEvent(CurrentWeatherData currentWeatherData) {
        setWeatherData(currentWeatherData);
        deleteOldWeatherCurrentData();
        setInRealm(currentWeatherData);
        mProgressDialog.dismiss();
        Utils.showMessage(mRootView, getString(R.string.update_success));
    }

    @Subscribe
    public void onErrorEvent(ErrorEventCurrentWeather errorEventCurrentWeather) {
        mProgressDialog.dismiss();
        Utils.showMessage(mRootView, getString(R.string.error_update));
    }

    private void setInRealm(CurrentWeatherData currentWeatherData) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(currentWeatherData);
        mRealm.commitTransaction();
    }

    private void deleteOldWeatherCurrentData() {
        if (mRealmResults.size() > 0) {
            mRealm.beginTransaction();
            CurrentWeatherData oldCurrentWeatherData = mRealmResults.get(0);
            oldCurrentWeatherData.getWeather().deleteAllFromRealm();
            oldCurrentWeatherData.getCoord().deleteFromRealm();
            oldCurrentWeatherData.getMain().deleteFromRealm();
            oldCurrentWeatherData.getWind().deleteFromRealm();
            oldCurrentWeatherData.getClouds().deleteFromRealm();
            oldCurrentWeatherData.getSys().deleteFromRealm();
            oldCurrentWeatherData.deleteFromRealm();
            mRealm.commitTransaction();
        }
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

        setDateLastUpdate(weatherData.getUpdateDate());

        mToolbar.setTitle(weatherData.getName());
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


