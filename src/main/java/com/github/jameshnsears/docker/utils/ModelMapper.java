package com.github.jameshnsears.docker.utils;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.ContainerCreate;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.models.Network;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class ModelMapper {
    private final Gson gson = new Gson();

    public AbstractList<Image> mapJsonIntoImages(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Image>>() {
                }.getType());
    }

    public AbstractList<Container> mapJsonIntoContainers(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Container>>() {
                }.getType());
    }

    public AbstractList<Network> mapJsonIntoNetworks(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Network>>() {
                }.getType());
    }

    public AbstractList<Network> mapJsonInto(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Network>>() {
                }.getType());
    }

    public String mapConfigurationContainerIntoJson(Configuration configurationContainer) {
        Preconditions.checkNotNull(configurationContainer);

        ContainerCreate containerCreate = new ContainerCreate();
        containerCreate.image = configurationContainer.getImage();

        ///////////

        containerCreate.cmd = new ArrayList<>();
        for (String cmd: configurationContainer.getCommand().split("\\s+")) {
            containerCreate.cmd.add(cmd);
        }

        ///////////

        containerCreate.hostConfig = new ContainerCreate.HostConfig();

        containerCreate.hostConfig.portBindings = new HashMap<>();

        containerCreate.exposedPorts = new HashMap<>();
        for (Map.Entry<String, Integer> port: configurationContainer.getPorts().entrySet()) {
            containerCreate.exposedPorts.put(port.getKey(), new HashMap<>());

            List<Map<String, String>> portBindingsList= new ArrayList<>();
            Map<String, String> portBidningsMap = new HashMap<>();
            portBidningsMap.put("HostIp", "");
            portBidningsMap.put("HostPort", port.getValue().toString());

            portBindingsList.add(portBidningsMap);

            containerCreate.hostConfig.portBindings.put(port.getKey(), portBindingsList);
        }

        ///////////


        containerCreate.hostConfig.binds = new ArrayList<>();

        containerCreate.volumes = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> volumeMapEntry: configurationContainer.getVolumes().entrySet()) {
            Map<String, String> volumeMap = volumeMapEntry.getValue();
            containerCreate.volumes.put(volumeMap.get("bind") , new HashMap<>());

            containerCreate.hostConfig.binds.add(
                    String.format("%s:%s:%s",
                            configurationContainer.getName(),
                            volumeMap.get("bind"),
                            volumeMap.get("mode")));
        }

        ///////////

        Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        return gsonPrettyPrinter.toJson(containerCreate);
    }
}
