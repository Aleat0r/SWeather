package com.aleat0r.weather.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Aleksandr Kovalenko on 15.05.2016.
 */
public class WeatherPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public WeatherPagerAdapter(FragmentManager fm, @NonNull List<Fragment> pages) {
        super(fm);
        mFragments = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}