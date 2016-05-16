package com.aleat0r.weather.network;

import com.aleat0r.weather.bus.BusProvider;
import com.aleat0r.weather.bus.event.ErrorEvent;
import com.aleat0r.weather.pojo.weather.current.CurrentWeatherData;
import com.aleat0r.weather.pojo.weather.forecast.ForecastWeatherData;

import java.util.Date;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandr Kovalenko on 13.05.2016.
 */
public class ApiController {

    private static OpenWeatherApiService openWeatherApiService;

    public static OpenWeatherApiService getOpenWeatherApiService() {
        if (openWeatherApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .client(getLoggingInterceptor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            openWeatherApiService = retrofit.create(OpenWeatherApiService.class);
        }
        return openWeatherApiService;
    }

    private static OkHttpClient getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    public static void getCurrentWeather(Map<String, Object> params) {
        Call<CurrentWeatherData> call = ApiController.getOpenWeatherApiService().loadCurrentWeather(params);
        call.enqueue(new Callback<CurrentWeatherData>() {

            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if (response.isSuccessful()) {
                    CurrentWeatherData currentWeatherData = response.body();
                    currentWeatherData.setUpdateDate(new Date());
                    BusProvider.getInstance().post(currentWeatherData);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent());
            }
        });
    }

    public static void getForecastWeather(Map<String, Object> params) {
        Call<ForecastWeatherData> call = ApiController.getOpenWeatherApiService().loadForecastWeather(params);
        call.enqueue(new Callback<ForecastWeatherData>() {

            @Override
            public void onResponse(Call<ForecastWeatherData> call, Response<ForecastWeatherData> response) {
                if (response.isSuccessful()) {
                    ForecastWeatherData forecastWeatherData = response.body();
                    forecastWeatherData.setUpdateDate(new Date());
                    BusProvider.getInstance().post(forecastWeatherData);
                }
            }

            @Override
            public void onFailure(Call<ForecastWeatherData> call, Throwable t) {
                BusProvider.getInstance().post(new ErrorEvent());
            }
        });
    }

}
