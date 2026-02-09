package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> dataList;
    Button addCityButton;
    Button deleteCityButton;
    City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);

        // Create sample cities
        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Calgary", "Montreal", "Ottawa", "Winnipeg", "Quebec City"};
        String[] provinces = {"Alberta", "British Columbia", "Ontario", "Alberta", "Quebec", "Ontario", "Manitoba", "Quebec"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // Set up custom adapter
        cityAdapter = new CustomList(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add city button - opens dialog to add new city
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
            }
        });

        // Delete city button - deletes selected city
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCity != null) {
                    dataList.remove(selectedCity);
                    cityAdapter.notifyDataSetChanged();
                    selectedCity = null;
                }
            }
        });

        // Click listener for selecting a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCity = dataList.get(position);
            }
        });

        // Long click listener for EDITING a city (LAB 3 REQUIREMENT)
        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                City cityToEdit = dataList.get(position);
                AddCityFragment fragment = AddCityFragment.newInstance(cityToEdit);
                fragment.show(getSupportFragmentManager(), "EDIT_CITY");
                return true;
            }
        });
    }

    @Override
    public void onOkPressed(City city) {
        // Check if city already exists in list (editing case)
        if (!dataList.contains(city)) {
            // New city - add it
            dataList.add(city);
        }
        // If editing, the city object was already modified in the fragment
        cityAdapter.notifyDataSetChanged();
    }
}
//Citation: https://claude.ai/share/882c31a9-6194-428b-a800-36599f6b586a