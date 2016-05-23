package com.aleat0r.weather.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.aleat0r.weather.R;
import com.aleat0r.weather.ui.adapter.PlaceAutocompleteAdapter;
import com.aleat0r.weather.util.PreferencesUtils;
import com.aleat0r.weather.util.SingleShotLocationProvider;
import com.aleat0r.weather.util.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Aleksandr Kovalenko on 19.05.2016.
 */
public class LocationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private CoordinatorLayout mRootView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locatin);

        mRootView = (CoordinatorLayout) findViewById(R.id.coord_view);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        initViews();
    }

    private void initViews() {
        initToolbar();
        initAutocompleteView();
        initButton();
        mProgressDialog = Utils.createProgressDialog(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.location_activity_label);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initAutocompleteView() {
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete_places);
        autocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        mAdapter = new PlaceAutocompleteAdapter(this, mRootView, mGoogleApiClient, autocompleteFilter);
        autocompleteView.setAdapter(mAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AutocompletePrediction item = mAdapter.getItem(position);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, item.getPlaceId());
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            LatLng location = places.get(0).getLatLng();
            PreferencesUtils.setLocation(LocationActivity.this, location.longitude, location.latitude);
            places.release();
            Utils.hideKeyboard(LocationActivity.this);
            returnWithOkResult();
        }
    };

    private void returnWithOkResult() {
        setResult(RESULT_OK);
        this.finish();
    }

    private void initButton() {
        Button btnUseCurrentLocation = (Button) findViewById(R.id.btn_use_location);
        btnUseCurrentLocation.setOnClickListener(this);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Utils.showMessage(mRootView, getString(R.string.error_connect_google_api) + getString(R.string.empty_place) + connectionResult.getErrorCode());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_use_location:
                Utils.hideKeyboard(LocationActivity.this);
                getCurrentLocation();
                break;
            default:
                break;
        }
    }

    private void getCurrentLocation() {
        mProgressDialog.show();
        SingleShotLocationProvider.requestSingleUpdate(this, mRootView, new SingleShotLocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                if (location != null) {
                    PreferencesUtils.setLocation(LocationActivity.this, location.longitude, location.latitude);
                    mProgressDialog.dismiss();
                    returnWithOkResult();
                }
            }
        });
    }
}
