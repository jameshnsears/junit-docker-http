package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeResponse {
    @SerializedName("Volumes")
    @Expose
    private List<VolumeName> volumes;

    public List<VolumeName> getVolumes() {
        return volumes;
    }

    public void setVolumes(final List<VolumeName> volumes) {
        this.volumes = volumes;
    }

    class VolumeName {
        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
