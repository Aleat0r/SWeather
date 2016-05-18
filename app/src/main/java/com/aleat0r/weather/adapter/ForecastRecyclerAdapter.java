package com.aleat0r.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleat0r.weather.R;
import com.aleat0r.weather.network.ApiConstants;
import com.aleat0r.weather.realm.weather.forecast.WeatherList;
import com.aleat0r.weather.util.Utils;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Aleksandr Kovalenko on 16.05.2016.
 */

public class ForecastRecyclerAdapter extends RealmRecyclerViewAdapter<WeatherList, ForecastRecyclerAdapter.ForecastViewHolder> {

    private Context mContext;

    public ForecastRecyclerAdapter(Context context, OrderedRealmCollection<WeatherList> forecastList) {
        super(context, forecastList, true);
        mContext = context;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_card_rv_forecast, parent, false);
        return new ForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

        WeatherList forecastData = getData().get(position);

        String weatherTypeIconUrl = ApiConstants.ICON_WEATHER_TYPE_URL + forecastData.getWeather().get(0).getIcon() + mContext.getString(R.string.image_format);
        Picasso.with(mContext).load(weatherTypeIconUrl).placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error_loading).into(holder.mImgWeatherType);

        holder.mTvDate.setText(Utils.getForecastDateFromUnix(mContext, forecastData.getDt()));

        holder.mTvWeatherDescription.setText(forecastData.getWeather().get(0).getDescription());
        holder.mTvTemperature.setText(String.valueOf(forecastData.getTemp().getDay() + mContext.getString(R.string.empty_place) + mContext.getString(R.string.degrees_celsius)));
        holder.mTvWind.setText(String.valueOf(forecastData.getSpeed()) + mContext.getString(R.string.empty_place) + mContext.getString(R.string.metric_speed));
        holder.mTvHumidity.setText(String.valueOf(forecastData.getHumidity()) + mContext.getString(R.string.empty_place) + mContext.getString(R.string.percent));
        holder.mTvCloudiness.setText(String.valueOf(forecastData.getClouds()) + mContext.getString(R.string.empty_place) + mContext.getString(R.string.percent));
        holder.mTvMinTemperature.setText(String.valueOf(forecastData.getTemp().getMin() + mContext.getString(R.string.empty_place) + mContext.getString(R.string.degrees_celsius)));
        holder.mTvMaxTemperature.setText(String.valueOf(forecastData.getTemp().getMax() + mContext.getString(R.string.empty_place) + mContext.getString(R.string.degrees_celsius)));
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImgWeatherType;
        private TextView mTvDate;
        private TextView mTvWeatherDescription;
        private TextView mTvTemperature;
        private TextView mTvWind;
        private TextView mTvHumidity;
        private TextView mTvCloudiness;
        private TextView mTvMinTemperature;
        private TextView mTvMaxTemperature;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            mImgWeatherType = (ImageView) itemView.findViewById(R.id.img_weather_type);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvWeatherDescription = (TextView) itemView.findViewById(R.id.tv_weather_description);
            mTvTemperature = (TextView) itemView.findViewById(R.id.tv_temperature);
            mTvWind = (TextView) itemView.findViewById(R.id.tv_wind);
            mTvHumidity = (TextView) itemView.findViewById(R.id.tv_humidity);
            mTvCloudiness = (TextView) itemView.findViewById(R.id.tv_cloudiness);
            mTvMinTemperature = (TextView) itemView.findViewById(R.id.tv_min_temperature);
            mTvMaxTemperature = (TextView) itemView.findViewById(R.id.tv_max_temperature);
        }
    }
}


