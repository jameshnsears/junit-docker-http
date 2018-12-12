package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkResponse {
    @SerializedName("Name")
    @Expose
    public String name = "";
}
