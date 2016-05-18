package com.aleat0r.weather.realm.weather.current;

import com.aleat0r.weather.realm.weather.Coord;
import com.aleat0r.weather.realm.weather.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class CurrentWeatherData extends RealmObject {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private RealmList<Weather> weather;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private int cod;
    @SerializedName("message")
    @Expose
    private int message;

    private long updateDate;

    /**
     * @return The coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * @param coord The coord
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
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
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The main
     */
    public Main getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @param wind The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The dt
     */
    public int getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(int dt) {
        this.dt = dt;
    }

    /**
     * @return The sys
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * @param sys The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The cod
     */
    public int getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(int cod) {
        this.cod = cod;
    }

    /**
     * @return The message
     */
    public int getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(int message) {
        this.message = message;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
