package com.github.jameshnsears.docker.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.docker.models.ContainerCreateRequest;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestMapper {
    public String containerCreateRequest(Configuration configurationContainer) {
        Preconditions.checkNotNull(configurationContainer);

        final ContainerCreateRequest containerCreateRequest = new ContainerCreateRequest();
        containerCreateRequest.hostConfig = new ContainerCreateRequest.HostConfig();

        containerCreateRequest.image = configurationContainer.image;
        cmd(configurationContainer, containerCreateRequest);
        network(configurationContainer, containerCreateRequest);
        ports(configurationContainer, containerCreateRequest);
        volumes(configurationContainer, containerCreateRequest);

        final Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        return gsonPrettyPrinter.toJson(containerCreateRequest);
    }

    private void volumes(Configuration configurationContainer, ContainerCreateRequest containerCreateRequest) {
        Preconditions.checkNotNull(configurationContainer);
        Preconditions.checkNotNull(containerCreateRequest);

        containerCreateRequest.hostConfig.binds = new ArrayList<>();

        containerCreateRequest.volumes = new HashMap<>();
        for (final Map.Entry<String, Map<String, String>> volumeMapEntry : configurationContainer.volumes.entrySet()) {
            final Map<String, String> volumeMap = volumeMapEntry.getValue();
            containerCreateRequest.volumes.put(volumeMap.get("bind"), new HashMap<>());

            containerCreateRequest.hostConfig.binds.add(
                    String.format("%s:%s:%s",
                            configurationContainer.name,
                            volumeMap.get("bind"),
                            volumeMap.get("mode")));
        }
    }

    private void network(Configuration configurationContainer, ContainerCreateRequest containerCreateRequest) {
        Preconditions.checkNotNull(configurationContainer);
        Preconditions.checkNotNull(containerCreateRequest);

        if (!configurationContainer.network.isEmpty()) {
            containerCreateRequest.hostConfig.networkMode = configurationContainer.network;
        }
    }

    private void ports(Configuration configurationContainer, ContainerCreateRequest containerCreateRequest) {
        Preconditions.checkNotNull(configurationContainer);
        Preconditions.checkNotNull(containerCreateRequest);

        containerCreateRequest.hostConfig.portBindings = new HashMap<>();

        containerCreateRequest.exposedPorts = new HashMap<>();
        for (final Map.Entry<String, Integer> port : configurationContainer.ports.entrySet()) {
            containerCreateRequest.exposedPorts.put(port.getKey(), new HashMap<>());

            final List<Map<String, String>> portBindingsList = new ArrayList<>();
            if (Integer.valueOf(port.getValue()) != 0) {
                final Map<String, String> portBindingsMap = new HashMap<>();
                portBindingsMap.put("HostIp", "");
                portBindingsMap.put("HostPort", port.getValue().toString());
                    portBindingsList.add(portBindingsMap);
            }

            containerCreateRequest.hostConfig.portBindings.put(port.getKey(), portBindingsList);
        }
    }

    private void cmd(Configuration configurationContainer, ContainerCreateRequest containerCreateRequest) {
        Preconditions.checkNotNull(configurationContainer);
        Preconditions.checkNotNull(containerCreateRequest);

        if (!configurationContainer.command.isEmpty()) {
            containerCreateRequest.cmd = new ArrayList<>();
            for (final String cmd : configurationContainer.command.split("\\s+")) {
                containerCreateRequest.cmd.add(cmd);
            }
        }
    }
}
