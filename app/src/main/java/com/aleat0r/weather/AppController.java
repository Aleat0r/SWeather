package com.aleat0r.weather;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Aleksandr Kovalenko on 17.05.2016.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        configureRealm();
    }

    private void configureRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}