package com.weatherapp;

import com.google.gson.annotations.SerializedName;

public class LocationResult {
    @SerializedName("name")
    public String name;

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;
}
