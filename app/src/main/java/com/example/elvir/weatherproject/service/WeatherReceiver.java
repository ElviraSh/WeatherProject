package com.example.elvir.weatherproject.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class WeatherReceiver extends BroadcastReceiver {

    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";

    public static final String STATUS_OK = "ok";
    public static final String STATUS_FAIL = "fail";

    private WeatherReceiverListener listener;

    public WeatherReceiver(WeatherReceiverListener listener) {
        this.listener = listener;
    }

    public WeatherReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.onWeatherReceive(intent.getStringExtra(KEY_STATUS), intent.getStringExtra(KEY_MESSAGE));
        }
    }


    public WeatherReceiverListener getListener() {
        return listener;
    }

    public void setListener(WeatherReceiverListener listener) {
        this.listener = listener;
    }

    public interface WeatherReceiverListener {

        void onWeatherReceive(String status, String message);
    }
}
