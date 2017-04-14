package com.example.elvir.weatherproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elvir.weatherproject.repo.data.CityRepo;
import com.example.elvir.weatherproject.repo.data.ContentProviderCity;
import com.example.elvir.weatherproject.repo.data.ContentProviderWeather;
import com.example.elvir.weatherproject.repo.data.WeatherInt;
import com.example.elvir.weatherproject.resyclerview.CityAdapter;
import com.example.elvir.weatherproject.resyclerview.CityItem;
import com.example.elvir.weatherproject.model.City;
import com.example.elvir.weatherproject.repo.table.CityContract;
import com.example.elvir.weatherproject.model.StringSurch;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CityItem {

    private RecyclerView cityRecyclerView;
    private Button btnAddCity;
    private EditText etCityName;
    private CityRepo cityRepo;
    private WeatherInt weatherInt;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRepositories();
        initRecyclerView();
        updateCities();
    }

    private void initRepositories() {
        cityRepo = new ContentProviderCity(this);
        weatherInt = new ContentProviderWeather(this);
    }


    private void initRecyclerView() {
        cityAdapter = new CityAdapter();
        cityAdapter.setListener(this);
        cityRecyclerView.setAdapter(cityAdapter);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViews() {
        cityRecyclerView = (RecyclerView) findViewById(R.id.rv);
        btnAddCity = (Button) findViewById(R.id.add);
        btnAddCity.setOnClickListener(this);
        etCityName = (EditText) findViewById(R.id.name);
    }

    private void updateCities() {
        List<City> cityList = cityRepo.getAll();
        cityAdapter.setList(cityList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                btnAddCityClicked(v);
                break;
        }
    }

    private void btnAddCityClicked(View v) {

        String cityName = StringSurch.deleteSpacesLeftAndRight(etCityName.getText().toString());

        if (StringSurch.isEmptyOrSpace(cityName)) {
            toast("city name is empty", Toast.LENGTH_SHORT);
        } else {
            addCity(cityName);
        }
    }

    private void addCity(String cityName) {
        City city = new City(cityName);
        cityRepo.add(city);
        updateCities();
    }

    private void removeCity(City city) {
        weatherInt.remove(city.getName());
        cityRepo.remove(city);
        cityAdapter.remove(city);

    }
    private void toast(String string, int length) {
        Toast.makeText(this, string, length).show();
    }


    @Override
    public void onDeleteCityItemClick(City city) {
        removeCity(city);
    }

    @Override
    public void onItemClickListener(City city) {
        Intent intent = new Intent(this, CityInfoActivity.class);
        CityContract.insertToIntent(intent, city);
        startActivity(intent);
    }
}
