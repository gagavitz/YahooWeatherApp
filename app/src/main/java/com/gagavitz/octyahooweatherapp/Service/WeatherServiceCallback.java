package com.gagavitz.octyahooweatherapp.Service;

import com.gagavitz.octyahooweatherapp.Data.Channel;

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
