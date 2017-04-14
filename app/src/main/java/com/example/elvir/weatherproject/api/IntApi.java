package com.example.elvir.weatherproject.api;


import com.example.elvir.weatherproject.api.pojo.WeatherPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IntApi {

    String BASE_URL = "http://api.openweathermap.org";

    @GET("data/2.5/weather?appid=ed41b67721a8411ecc8eb56a0a01fb10")
    Call<WeatherPojo> getWeather(@Query("q") String cityName);

}
