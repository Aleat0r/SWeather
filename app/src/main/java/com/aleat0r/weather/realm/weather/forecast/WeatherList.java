package com.aleat0r.weather.realm.weather.forecast;

import com.aleat0r.weather.realm.weather.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Aleksandr Kovalenko on 18.05.2016.
 */
public class WeatherList extends RealmObject {

    @SerializedName("dt")
    @Expose
    private long dt;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("weather")
    @Expose
    private RealmList<Weather> weather;
    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private int deg;
    @SerializedName("clouds")
    @Expose
    private int clouds;
    @SerializedName("rain")
    @Expose
    private double rain;

    /**
     * @return The dt
     */
    public long getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(int dt) {
        this.dt = dt;
    }

    /**
     * @return The temp
     */
    public Temp getTemp() {
        return temp;
    }

    /**
     * @param temp The temp
     */
    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    /**
     * @return The pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return The humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The weather
     */
    public RealmList<Weather> getWeather() {
        return weather;
    }

    /**
     * @param weather The weather
     */
    public void setWeather(RealmList<Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return The speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed The speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return The deg
     */
    public int getDeg() {
        return deg;
    }

    /**
     * @param deg The deg
     */
    public void setDeg(int deg) {
        this.deg = deg;
    }

    /**
     * @return The clouds
     */
    public int getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The rain
     */
    public double getRain() {
        return rain;
    }

    /**
     * @param rain The rain
     */
    public void setRain(double rain) {
        this.rain = rain;
    }

}
