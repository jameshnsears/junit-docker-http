package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VolumeResponse {
    @SerializedName("Volumes")
    @Expose
    public List<VolumeName> volumes = new ArrayList<>();

    private class VolumeName {
        @SerializedName("Name")
        @Expose
        public String name = "";
    }
}
