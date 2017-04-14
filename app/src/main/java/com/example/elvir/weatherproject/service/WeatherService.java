package com.example.elvir.weatherproject.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.elvir.weatherproject.repo.data.CityRepo;
import com.example.elvir.weatherproject.repo.data.ContentProviderCity;
import com.example.elvir.weatherproject.repo.data.ContentProviderWeather;
import com.example.elvir.weatherproject.repo.data.WeatherInt;
import com.example.elvir.weatherproject.model.City;
import com.example.elvir.weatherproject.model.Weather;
import com.example.elvir.weatherproject.repo.table.CityContract;
import com.example.elvir.weatherproject.api.IntApi;
import com.example.elvir.weatherproject.api.pojo.WeatherPojo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService extends IntentService {

    public static final String ACTION_UPDATED = "com.zlthnrtm.hw3_weathercontentprovider.service.WeatherService";

    public WeatherService() {
        super(WeatherService.class.getName());
    }

    public static void start(@NonNull Context context, @NonNull City city) {

        Intent intent = new Intent(context, WeatherService.class);
        CityContract.insertToIntent(intent, city);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherInt weatherInt = new ContentProviderWeather(getApplicationContext());
        CityRepo cityRepo = new ContentProviderCity(getApplicationContext());

        String status = WeatherReceiver.STATUS_OK;
        String message = "";
        List<City> cityList = cityRepo.getAll();
        City city = CityContract.fromIntent(intent);
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IntApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IntApi weatherApi = retrofit.create(IntApi.class);
            Call<WeatherPojo> weatherCall = weatherApi.getWeather(city.getName());
            Response<WeatherPojo> response = weatherCall.execute();
            int code = response.code();
            if (response.isSuccessful() && (code < 400)) {
                WeatherPojo weatherPojo = response.body();
                Weather weather = new Weather(
                        new Date(System.currentTimeMillis()),
                        city.getName(),
                        weatherPojo.getSys().getCountry(),
                        weatherPojo.getMain().getTemp() - 273.15);
                weatherInt.remove(city.getName());
                weatherInt.add(weather);
            } else {
                status = WeatherReceiver.STATUS_FAIL;
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            status = WeatherReceiver.STATUS_FAIL;
            message = e.getMessage();
        }

        Intent broadcastIntent = new Intent(ACTION_UPDATED);
        broadcastIntent.putExtra(WeatherReceiver.KEY_STATUS, status);
        broadcastIntent.putExtra(WeatherReceiver.KEY_MESSAGE, message);
        sendBroadcast(broadcastIntent);
    }
}
