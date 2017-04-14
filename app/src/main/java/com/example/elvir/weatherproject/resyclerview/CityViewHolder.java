package com.example.elvir.weatherproject.resyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.elvir.weatherproject.R;
import com.example.elvir.weatherproject.model.City;


public class CityViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCityName;

    private Button btnDelete;

    public CityViewHolder(View itemView) {
        super(itemView);
        tvCityName = (TextView) itemView.findViewById(R.id.city_name);
        btnDelete = (Button) itemView.findViewById(R.id.delete);
    }

    public void bind(@NonNull final City city, @Nullable final CityItem listener) {
        tvCityName.setText(city.getName());

        if (listener != null) {
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteCityItemClick(city);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(city);
                }
            });
        }
    }
}
