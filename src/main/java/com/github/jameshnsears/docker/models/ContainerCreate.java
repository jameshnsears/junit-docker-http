package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ContainerCreate {
    @SerializedName("Tty")
    @Expose
    public Boolean tty = false;

    @SerializedName("OpenStdin")
    @Expose
    public Boolean openStdin = false;

    @SerializedName("StdinOnce")
    @Expose
    public Boolean sttdinOnce = false;

    @SerializedName("AttachStdin")
    @Expose
    public Boolean attachStdin = false;

    @SerializedName("AttachStdout")
    @Expose
    public Boolean attachStdout = false;

    @SerializedName("AttachStderr")
    @Expose
    public Boolean attachStderr = false;

    @SerializedName("NetworkDisabled")
    @Expose
    public Boolean networkDisabled = false;

    @SerializedName("Image")
    @Expose
    public String image;

    @SerializedName("ExposedPorts")
    @Expose
    public Map<String, Map<String, String>> exposedPorts;

    @SerializedName("Cmd")
    @Expose
    public List<String> cmd;

    @SerializedName("Volumes")
    @Expose
    public Map<String, Map<String, String>> volumes;

    @SerializedName("HostConfig")
    @Expose
    public HostConfig hostConfig;

    public static class HostConfig {
        @SerializedName("NetworkMode")
        @Expose
        public String cmd = "default";

        @SerializedName("Binds")
        @Expose
        public List<String> binds;

        @SerializedName("PortBindings")
        @Expose
        public Map<String, List<Map<String, String>>> portBindings;

    }
}
