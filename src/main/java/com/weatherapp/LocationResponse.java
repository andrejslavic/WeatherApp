package com.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LocationResponse {
    @SerializedName("results")
    public List<LocationResult> results;
}
