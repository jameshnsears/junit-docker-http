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

import java.util.AbstractList;
import java.util.Collection;

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

        ContainerCreate containerCreate= new ContainerCreate();

        Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        return gsonPrettyPrinter.toJson(containerCreate);
    }
}
