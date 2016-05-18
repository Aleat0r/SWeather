package com.aleat0r.weather.network;

import com.aleat0r.weather.realm.weather.forecast.ForecastWeatherData;
import com.aleat0r.weather.realm.weather.current.CurrentWeatherData;

import java.util.Map;

import retrofit2.Call;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Aleksandr Kovalenko on 13.05.2016.
 */
public interface OpenWeatherApiService {

    @POST("weather")
    Call<CurrentWeatherData> loadCurrentWeather(@QueryMap Map<String, Object> params);

    @POST("forecast/daily")
    Call<ForecastWeatherData> loadForecastWeather(@QueryMap Map<String, Object> params);


}
