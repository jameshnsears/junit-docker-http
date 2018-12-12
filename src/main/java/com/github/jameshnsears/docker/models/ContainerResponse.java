package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContainerResponse {
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("ImageResponse")
    @Expose
    private String image;

    @SerializedName("Names")
    @Expose
    private List<String> names;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public List<String> getNames() {
        List<String> response;
        if (names == null) {
            response = new ArrayList<>();
        } else {
            response = names;
        }
        return response;
    }

    public void setNames(final List<String> names) {
        this.names = names;
    }
}
