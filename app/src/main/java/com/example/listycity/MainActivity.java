package com.example.listycity;  // Change to YOUR package name

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declare references
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton;
    Button deleteCityButton;
    int selectedPosition = -1;  // Track selected city position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find references
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);

        // Create string array of cities
        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Calgary",
                "Montreal", "Ottawa", "Winnipeg", "Quebec City"};

        // Create ArrayList and add data
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // Create ArrayAdapter
        cityAdapter = new ArrayAdapter<>(this, R.layout.content,
                R.id.city_text, dataList);

        // Set adapter to ListView
        cityList.setAdapter(cityAdapter);

        // Set up click listener for selecting a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update selected position
                selectedPosition = position;

                // Highlight selected item
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.LTGRAY);

                Toast.makeText(MainActivity.this,
                        "Selected: " + dataList.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Add City Button Click Listener
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCityDialog();
            }
        });

        // Delete City Button Click Listener
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedCity();
            }
        });
    }

    // Method to show dialog for adding a city
    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        // Create EditText for user input
        final EditText input = new EditText(this);
        input.setHint("Enter city name");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("CONFIRM", (dialog, which) -> {
            String cityName = input.getText().toString().trim();
            if (!cityName.isEmpty()) {
                dataList.add(cityName);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,
                        cityName + " added!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Please enter a city name",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to delete selected city
    private void deleteSelectedCity() {
        if (selectedPosition != -1 && selectedPosition < dataList.size()) {
            String cityName = dataList.get(selectedPosition);
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            Toast.makeText(this,
                    cityName + " deleted!",
                    Toast.LENGTH_SHORT).show();

            selectedPosition = -1;  // Reset selection
        } else {
            Toast.makeText(this,
                    "Please select a city first",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
//Citation:https://claude.ai/share/b7631409-5059-4896-b237-c8677e8e8034