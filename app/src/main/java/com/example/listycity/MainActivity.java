package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private City selectedCity;

    @Override
    public void addCity(City city) {
        // Check if city already exists in list (editing case)
        if (!dataList.contains(city)) {
            // New city - add it
            cityAdapter.add(city);
        }
        // If editing, the city object was already modified in the fragment
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample cities with provinces
        String[] cities = { "Edmonton", "Vancouver", "Toronto", "Calgary", "Montreal", "Ottawa", "Winnipeg", "Quebec City" };
        String[] provinces = { "Alberta", "British Columbia", "Ontario", "Alberta", "Quebec", "Ontario", "Manitoba", "Quebec" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add city button (FloatingActionButton)
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // Lab 3: Click listener for selecting a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCity = dataList.get(position);
            }
        });

        // Lab 3: Long click listener for EDITING a city
        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                City cityToEdit = dataList.get(position);
                AddCityFragment fragment = AddCityFragment.newInstance(cityToEdit);
                fragment.show(getSupportFragmentManager(), "Edit City");
                return true;
            }
        });
    }
}