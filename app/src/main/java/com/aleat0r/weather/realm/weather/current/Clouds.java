package com.aleat0r.weather.realm.weather.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Clouds extends RealmObject {

    @SerializedName("all")
    @Expose
    private int all;

    /**
     * @return The all
     */
    public int getAll() {
        return all;
    }

    /**
     * @param all The all
     */
    public void setAll(int all) {
        this.all = all;
    }

}
