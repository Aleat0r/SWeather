package com.aleat0r.weather.bus;

import android.util.Log;

import com.squareup.otto.Bus;

/**
 * Created by Aleksandr Kovalenko on 14.05.2016.
 */
public class BusProvider {

    private static BusProvider sBusProvider;
    private Bus mBus;
    public static final String LOG_BUS_MESSAGE = "object == null and did not post";

    public static BusProvider getInstance() {
        if (sBusProvider == null) {
            sBusProvider = new BusProvider();
        }
        return sBusProvider;
    }

    private BusProvider() {
        mBus = new Bus();
        mBus.register(this);
    }

    public void register(Object object) {
        mBus.register(object);
    }

    public void unregister(Object object) {
        mBus.unregister(object);
    }

    public void post(Object object) {
        if (object != null) {
            mBus.post(object);
        } else {
            Log.d(BusProvider.class.getSimpleName(), LOG_BUS_MESSAGE);
        }
    }
}
