package com.aleat0r.weather.realm.weather.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ForecastWeatherData extends RealmObject {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private RealmList<WeatherList> weatherList;

    private long updateDate;

    /**
     * @return The city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return The message
     */
    public double getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(double message) {
        this.message = message;
    }

    /**
     * @return The cnt
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * @param cnt The cnt
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * @return The list
     */
    public RealmList<WeatherList> getWeatherList() {
        return weatherList;
    }

    /**
     * @param list The list
     */
    public void setWeatherList(RealmList<WeatherList> list) {
        this.weatherList = weatherList;
    }


    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

}
