package com.aleat0r.weather.realm.weather.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Wind extends RealmObject {

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private double deg;

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
    public double getDeg() {
        return deg;
    }

    /**
     * @param deg The deg
     */
    public void setDeg(double deg) {
        this.deg = deg;
    }

}
