package com.github.jameshnsears.docker.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("Id")
    @Expose
    public String id = "";

    @SerializedName("RepoTags")
    @Expose
    public List<String> repoTags = new ArrayList<>();
}
