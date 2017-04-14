package com.example.elvir.weatherproject.repo.data;

import com.example.elvir.weatherproject.model.City;

import java.util.List;

public interface CityRepo {

    List<City> getAll();
    void add(City city);
    void remove(City city);

}
