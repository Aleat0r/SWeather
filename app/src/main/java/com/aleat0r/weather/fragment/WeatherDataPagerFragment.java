package com.aleat0r.weather.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleat0r.weather.R;
import com.aleat0r.weather.adapter.WeatherPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr Kovalenko on 15.05.2016.
 */
public class WeatherDataPagerFragment extends Fragment {

    private List<Fragment> mFragments;
    private List<String> mFragmentNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_data_pager, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initFragments();
        initViewPager(view);
    }

    private void initFragments() {
        mFragments = new ArrayList<>(1);
        mFragmentNames = new ArrayList<>(1);
        mFragments.add(new CurrentWeatherFragment());
        mFragmentNames.add(getString(R.string.tub_current_weather));
    }

    private void initViewPager(View view) {
        final ViewPager vpWeather = (ViewPager) view.findViewById(R.id.vp_weather);
        final WeatherPagerAdapter weatherPagerAdapter = new WeatherPagerAdapter(getActivity().getSupportFragmentManager(), mFragments);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        if (tabLayout != null && vpWeather != null) {
            for (String fragmentName : mFragmentNames) {
                tabLayout.addTab(tabLayout.newTab().setText(fragmentName));
            }
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    vpWeather.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            vpWeather.setAdapter(weatherPagerAdapter);
            vpWeather.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }
}
