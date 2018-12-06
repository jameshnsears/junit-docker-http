package com.github.jameshnsears.docker.utils;

import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;

public class ResponseMapper {
    private final Gson gson = new Gson();
    // private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ArrayList<Image> mapJsonIntoImageList(String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Image>>() {
                }.getType());
    }

    public ArrayList<Container> mapJsonIntoContainerList(String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Container>>() {
                }.getType());
    }
}
