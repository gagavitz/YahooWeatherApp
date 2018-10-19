package com.gagavitz.octyahooweatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gagavitz.octyahooweatherapp.Data.Channel;
import com.gagavitz.octyahooweatherapp.Data.Item;
import com.gagavitz.octyahooweatherapp.Service.WeatherServiceCallback;
import com.gagavitz.octyahooweatherapp.Service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView ivWeatherIcon;
    private TextView tvTemperature;
    private TextView tvLocation;
    private TextView tvCondition;
    private ImageView ivLogo;

    private YahooWeatherService service;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ivWeatherIcon = findViewById(R.id.ivWeatherIcon);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvCondition = findViewById(R.id.tvCondition);
        tvLocation = findViewById(R.id.tvLocation);
        ivLogo = findViewById(R.id.ivLogo);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service.refreshWeather("Richmond, CA");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/i" + channel.getItem().getCondition().getCode(), null, getPackageName());

        Drawable weatherIconDrawable = getDrawable(resourceId);
        Drawable yahooLogo = getDrawable(R.drawable.yahoo_logo);

        ivWeatherIcon.setImageDrawable(weatherIconDrawable);
        ivLogo.setImageDrawable(yahooLogo);
        tvLocation.setText(service.getLocation());
        tvTemperature.setText(item.getCondition().getTemperature()+"\u00B0 "+channel.getUnits().getTemperature());
        tvCondition.setText(item.getCondition().getDescription());
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        dialog.hide();
    }
}
