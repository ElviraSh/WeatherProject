package com.example.elvir.weatherproject.repo.data;

import android.content.Context;
import android.database.Cursor;

import com.example.elvir.weatherproject.model.City;
import com.example.elvir.weatherproject.model.Weather;
import com.example.elvir.weatherproject.repo.table.WeatherContract;

public class ContentProviderWeather implements WeatherInt {
    private Context context;

    public ContentProviderWeather(Context context) {
        this.context = context;
    }

    @Override
    public Weather get(City city) {
        Weather weather = null;
        Cursor cursor = context.getContentResolver().query(WeatherContract.getUri(),
                null,
                WeatherContract.WeatherEntry.COLUMN_CITY_NAME + "=?",
                new String[]{city.getName()}, null);
        while (cursor.moveToNext()) {
            weather = WeatherContract.fromCursor(cursor);
        }
        cursor.close();

        return weather;
    }

    @Override
    public void add(Weather weather) {
        context.getContentResolver().insert(WeatherContract.getUri(),
                WeatherContract.toContentValues(weather));
    }

    @Override
    public void remove(String cityName) {
        context.getContentResolver().delete(WeatherContract.getUri(),
                WeatherContract.WeatherEntry.COLUMN_CITY_NAME + "=?", new String[]{cityName});
    }
}
