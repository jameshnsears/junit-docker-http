package com.github.jameshnsears.docker.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContainerResponse {
    @SerializedName("Id")
    @Expose
    public String id = "";

    @SerializedName("ImageID")
    @Expose
    public String imageId = "";

    @SerializedName("Names")
    @Expose
    public List<String> names = new ArrayList<>();
}
