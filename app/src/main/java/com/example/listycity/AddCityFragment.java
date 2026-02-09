package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private EditText cityEditText;
    private EditText provinceEditText;
    private OnFragmentInteractionListener listener;
    private City cityToEdit;

    public interface OnFragmentInteractionListener {
        void onOkPressed(City city);
    }

    // Empty constructor for adding new city
    public AddCityFragment() {
    }

    // Method to create fragment for editing (preferred Android way)
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
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment, null);

        cityEditText = view.findViewById(R.id.city_edittext);
        provinceEditText = view.findViewById(R.id.province_edittext);

        // Check if we're editing an existing city
        Bundle args = getArguments();
        if (args != null) {
            cityToEdit = (City) args.getSerializable("CITY_KEY");
            if (cityToEdit != null) {
                // Pre-fill the fields with existing data
                cityEditText.setText(cityToEdit.getCityName());
                provinceEditText.setText(cityToEdit.getProvinceName());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String title = (cityToEdit != null) ? "Edit City" : "Add City";

        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cityName = cityEditText.getText().toString();
                        String provinceName = provinceEditText.getText().toString();

                        if (cityToEdit != null) {
                            // Editing existing city
                            cityToEdit.setCityName(cityName);
                            cityToEdit.setProvinceName(provinceName);
                            listener.onOkPressed(cityToEdit);
                        } else {
                            // Adding new city
                            City newCity = new City(cityName, provinceName);
                            listener.onOkPressed(newCity);
                        }
                    }
                }).create();
    }
}