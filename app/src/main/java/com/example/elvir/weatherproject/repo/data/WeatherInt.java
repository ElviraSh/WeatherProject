package com.example.elvir.weatherproject.repo.data;

import com.example.elvir.weatherproject.model.City;
import com.example.elvir.weatherproject.model.Weather;


public interface WeatherInt {


    Weather get(City city);

    void add(Weather weather);

    void remove(String cityName);


}
