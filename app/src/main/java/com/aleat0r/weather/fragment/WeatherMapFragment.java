package com.aleat0r.weather.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.aleat0r.weather.R;
import com.aleat0r.weather.activity.MainActivity;
import com.aleat0r.weather.util.Constants;

/**
 * Created by Aleksandr Kovalenko on 16.05.2016.
 */
public class WeatherMapFragment extends Fragment{

    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mToolbar = ((MainActivity) getActivity()).getToolbar();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_map, container, false);
        mToolbar.setTitle(R.string.weather_map);
        initWebView(view);
        return view;
    }

    private void initWebView(View view){
        mWebView = (WebView) view.findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        updateWeatherInfo();
    }

    private void updateWeatherInfo(){
        mWebView.loadUrl(Constants.URL_WEATHER_MAP_HTML);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
