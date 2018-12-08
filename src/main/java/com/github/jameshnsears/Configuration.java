package com.github.jameshnsears;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Configuration {
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ports")
    @Expose
    private Map<String, Integer> ports;

    @SerializedName("volumes")
    @Expose
    private Map<String, Map<String, String>> volumes;

    @SerializedName("command")
    @Expose
    private String command;

    @SerializedName("network")
    @Expose
    private String network;

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<String, Integer> getPorts() {
        return ports;
    }

    public void setPorts(final Map<String, Integer> ports) {
        this.ports = ports;
    }

    public Map<String, Map<String, String>> getVolumes() {
        return volumes;
    }

    public void setVolumes(final Map<String, Map<String, String>> volumes) {
        this.volumes = volumes;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(final String network) {
        this.network = network;
    }
}
