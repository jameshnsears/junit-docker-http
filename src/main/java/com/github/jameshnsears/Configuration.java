package com.github.jameshnsears;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuration {
    @SerializedName("image")
    @Expose
    public String image = "";

    @SerializedName("name")
    @Expose
    public String name = "";

    @SerializedName("ports")
    @Expose
    public Map<String, Integer> ports = new HashMap<>();

    @SerializedName("volumes")
    @Expose
    public Map<String, Map<String, String>> volumes = new HashMap<>();

    @SerializedName("command")
    @Expose
    public String command = "";

    @SerializedName("network")
    @Expose
    public String network = "";

}
