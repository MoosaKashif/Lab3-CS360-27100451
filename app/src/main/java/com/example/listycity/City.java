package com.example.listycity;

import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String provinceName;

    public City(String cityName, String provinceName) {
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    // Setters needed for Lab 3 editing functionality
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}