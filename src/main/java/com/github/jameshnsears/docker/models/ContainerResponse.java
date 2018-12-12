package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContainerResponse {
    @SerializedName("Id")
    @Expose
    public String id = "";

    @SerializedName("ImageResponse")
    @Expose
    public String image = "";

    @SerializedName("Names")
    @Expose
    public List<String> names = new ArrayList<>();
}
