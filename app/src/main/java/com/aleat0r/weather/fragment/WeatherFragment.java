package com.aleat0r.weather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.network.ApiController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr Kovalenko on 15.05.2016.
 */
public class WeatherFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void getWeatherByCoordinates(String queryApi, String longitude, String latitude) {
        Map params = getGeneralQueryParams();
        params.put(ApiConstants.QUERY_NAME_LONGITUDE, longitude);
        params.put(ApiConstants.QUERY_NAME_LATITUDE, latitude);
        loadWeatherData(queryApi, params);
    }

    protected void getWeatherByCity(String queryApi, String city) {
        Map params = getGeneralQueryParams();
        params.put(ApiConstants.QUERY_NAME_CITY, city);
        loadWeatherData(queryApi, params);
    }

    private Map getGeneralQueryParams() {
        Map<String, Object> params = new HashMap<>();
        params.put(ApiConstants.QUERY_NAME_APP_ID, ApiConstants.API_KEY);
        params.put(ApiConstants.QUERY_NAME_UNITS_OF_MEASURE, ApiConstants.METRIC_UNITS_OF_MEASURE);
        return params;
    }

    private void loadWeatherData(String queryApi, Map params) {
        switch (queryApi) {
            case ApiConstants.WEATHER_CURRENT:
                ApiController.getCurrentWeather(params);
                break;
            case ApiConstants.WEATHER_FORECAST:
                break;
            default:
                break;
        }
    }

}