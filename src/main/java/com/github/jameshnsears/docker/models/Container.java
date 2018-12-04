package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Container {
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("Names")
    @Expose
    private List<String> names = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getNames() {
        if (names == null)
            return new ArrayList<>();
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
