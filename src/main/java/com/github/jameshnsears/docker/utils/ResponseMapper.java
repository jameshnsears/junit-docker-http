package com.github.jameshnsears.docker.utils;

import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;

public class ResponseMapper {
    private Gson gson = new Gson();

    public ArrayList<Image> mapJsonIntoImageList(String json) {
        Preconditions.checkArgument(json != null);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Image>>() {
                }.getType());
    }

    public ArrayList<Container> mapJsonIntoContainerList(String json) {
        Preconditions.checkArgument(json != null);

        return gson.fromJson(
                json,
                new TypeToken<Collection<Container>>() {
                }.getType());
    }
}
