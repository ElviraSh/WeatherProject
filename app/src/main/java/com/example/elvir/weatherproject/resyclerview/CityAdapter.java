package com.example.elvir.weatherproject.resyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elvir.weatherproject.R;
import com.example.elvir.weatherproject.model.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<City> list;

    private CityItem listener;

    public CityAdapter() {
    }

    public CityAdapter(List<City> list, CityItem listener) {
        this.list = list;
        this.listener = listener;
        notifyDataSetChanged();
    }


    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        City city = list.get(position);
        if (city != null) {
            holder.bind(city, listener);
        }
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public void add(City city) {
        list.add(city);
        notifyDataSetChanged();
    }

    public void remove(City city) {
        list.remove(city);
        notifyDataSetChanged();
    }

    public void setList(List<City> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public void setListener(CityItem listener) {
        this.listener = listener;
    }
}
