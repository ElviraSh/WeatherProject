package com.example.elvir.weatherproject.repo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.elvir.weatherproject.repo.table.CityContract;
import com.example.elvir.weatherproject.repo.table.WeatherContract;



public class WeatherSQLiteOH extends SQLiteOpenHelper {

    public static final String DATA_BASE_NAME = "weather.db";

    public static final int CURRENT_VERSION = 1;

    public WeatherSQLiteOH(Context context) {
        super(context, DATA_BASE_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CityContract.createTable(db);
        WeatherContract.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
