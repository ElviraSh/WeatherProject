package com.example.elvir.weatherproject.repo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.elvir.weatherproject.repo.table.CityContract;
import com.example.elvir.weatherproject.repo.table.WeatherContract;


public class WeatherProvider extends ContentProvider {

    private WeatherSQLiteOH dboHelper;

    public static final String BASE_CONTENT_AUTHORITY = "com.example.elvir.weatherproject";

    public static Uri baseUri;

    private final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int URI_KEY_CITY = 2;

    public static final int URI_KEY_WEATHER = 4;


    public static Uri getBaseUri() {
        return baseUri;
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            dboHelper = new WeatherSQLiteOH(getContext());
            baseUri = Uri.parse("content://" + BASE_CONTENT_AUTHORITY);
            matcher.addURI(BASE_CONTENT_AUTHORITY, CityContract.TABLE_NAME, URI_KEY_CITY);
            matcher.addURI(BASE_CONTENT_AUTHORITY, WeatherContract.TABLE_NAME, URI_KEY_WEATHER);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getType(uri);
        Cursor cursor = dboHelper.getReadableDatabase()
                .query(tableName, projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchCode = matcher.match(uri);
        switch (matchCode) {
            case URI_KEY_CITY:
                return CityContract.TABLE_NAME;
            case URI_KEY_WEATHER:
                return WeatherContract.TABLE_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getType(uri);
        long id = dboHelper.getWritableDatabase().insert(tableName, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getType(uri);
        return dboHelper.getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getType(uri);
        return dboHelper.getWritableDatabase().update(tableName, values, selection, selectionArgs);
    }
}
