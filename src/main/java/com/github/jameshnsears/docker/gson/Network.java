package com.github.jameshnsears.docker.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network {
    @SerializedName("Name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
