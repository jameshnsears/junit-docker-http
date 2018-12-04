package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume {
    @SerializedName("Volumes")
    @Expose
    public List<VolumeName> volumes = null;

    public List<VolumeName> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeName> volumes) {
        this.volumes = volumes;
    }

    class VolumeName {
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
}