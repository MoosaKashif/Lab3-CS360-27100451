package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;
    private City cityToEdit;

    // Empty constructor for adding new city
    public AddCityFragment() {
    }

    // Lab 3: Method to create fragment for editing (preferred Android way)
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("CITY_KEY", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Lab 3: Check if we're editing an existing city
        Bundle args = getArguments();
        if (args != null) {
            cityToEdit = (City) args.getSerializable("CITY_KEY");
            if (cityToEdit != null) {
                // Pre-fill the fields with existing data
                editCityName.setText(cityToEdit.getName());
                editProvinceName.setText(cityToEdit.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String title = (cityToEdit != null) ? "Edit City" : "Add a city";
        String positiveButtonText = (cityToEdit != null) ? "Save" : "Add";

        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityToEdit != null) {
                        // Lab 3: Editing existing city
                        cityToEdit.setName(cityName);
                        cityToEdit.setProvince(provinceName);
                        listener.addCity(cityToEdit);
                    } else {
                        // Adding new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}