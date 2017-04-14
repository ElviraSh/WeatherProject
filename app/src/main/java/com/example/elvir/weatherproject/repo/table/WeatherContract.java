package com.example.elvir.weatherproject.repo.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.example.elvir.weatherproject.repo.data.WeatherProvider;
import com.example.elvir.weatherproject.model.Weather;

import java.util.Date;


public class WeatherContract {

    public static final String TABLE_NAME = "weathers";

    public static void createTable(@NonNull SQLiteDatabase db) {
        TableBuilder.create(TABLE_NAME)
                .intColumn(WeatherEntry._ID)
                .intColumn(WeatherEntry.COLUMN_DATE)
                .textColumn(WeatherEntry.COLUMN_CITY_NAME)
                .textColumn(WeatherEntry.COLUMN_COUNTRY_CODE)
                .realColumn(WeatherEntry.COLUMN_TEMP)
                .primaryKey(WeatherEntry.COLUMN_DATE, WeatherEntry.COLUMN_CITY_NAME)
                .execute(db);
    }


    public static Uri getUri() {
        return WeatherProvider.getBaseUri().buildUpon().appendPath(TABLE_NAME).build();
    }

    public static ContentValues toContentValues(Weather weather) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherEntry.COLUMN_DATE, weather.getDate().getTime());
        contentValues.put(WeatherEntry.COLUMN_CITY_NAME, weather.getCityName());
        contentValues.put(WeatherEntry.COLUMN_COUNTRY_CODE, weather.getCountryCode());
        contentValues.put(WeatherEntry.COLUMN_TEMP, weather.getTemp());

        return contentValues;
    }

    public static Weather fromCursor(Cursor cursor) {
        int cityNameIndex = cursor.getColumnIndex(WeatherEntry.COLUMN_CITY_NAME);
        int dateIndex = cursor.getColumnIndex(WeatherEntry.COLUMN_DATE);
        int countryCodeIndex = cursor.getColumnIndex(WeatherEntry.COLUMN_COUNTRY_CODE);
        int tempIndex = cursor.getColumnIndex(WeatherEntry.COLUMN_TEMP);
        String cityName = cursor.getString(cityNameIndex);
        Date date = new Date(cursor.getLong(dateIndex));
        String countryCode = cursor.getString(countryCodeIndex);
        Double temp = cursor.getDouble(tempIndex);
        return new Weather(date, cityName, countryCode, temp);
    }


    public static class WeatherEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_DATE = "date";

        public static final String COLUMN_CITY_NAME = "city_name";

        public static final String COLUMN_COUNTRY_CODE = "country_code";

        public static final String COLUMN_TEMP = "temperature";


    }
}
