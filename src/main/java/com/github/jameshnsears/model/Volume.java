package com.github.jameshnsears.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume {
    @SerializedName("Volumes")
    @Expose
    private List<VolumeName> volumes = null;

    public List<VolumeName> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeName> volumes) {
        this.volumes = volumes;
    }

    static class VolumeName {
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
