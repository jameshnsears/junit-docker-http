package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContainerCreateResponse {
    @SerializedName("Id")
    @Expose
    public String id = "";

    @SerializedName("Warnings")
    @Expose
    public String warnings = "";
}
