package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("RepoTags")
    @Expose
    private List<String> repoTags;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public List<String> getRepoTags() {
        return repoTags;
    }

    public void setRepoTags(final List<String> repoTags) {
        this.repoTags = repoTags;
    }
}
