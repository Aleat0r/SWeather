package com.aleat0r.weather.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aleat0r.weather.R;
import com.aleat0r.weather.fragment.WeatherDataPagerFragment;
import com.aleat0r.weather.util.Constants;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setFirstItem();
    }

    private void initViews() {
        initToolbar();
        initDrawer();
    }

    private void setFirstItem() {
        displayFragment(Constants.WEATHER_DATA_PAGER_FRAGMENT);
        mNavigationView.setCheckedItem(R.id.item_weather_data);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.empty_place);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_burger);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item_weather_data:
                            displayFragment(Constants.WEATHER_DATA_PAGER_FRAGMENT);
                            break;
                        case R.id.item_wether_map:
//                            displayFragment(Constants.WEATHER_MAP_FRAGMENT);
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void displayFragment(int fragmentNumber) {
        Fragment fragment = null;

        switch (fragmentNumber) {
            case Constants.WEATHER_DATA_PAGER_FRAGMENT:
                fragment = new WeatherDataPagerFragment();
                break;
            case Constants.WEATHER_MAP_FRAGMENT:
                break;
            default:
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
        fragmentManager.executePendingTransactions();
        mDrawerLayout.closeDrawers();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
