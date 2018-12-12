package com.github.jameshnsears.docker.utils;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.docker.models.*;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;

public class ResponseMapper {
    private final Gson gson = new Gson();

    public AbstractList<ImageResponse> imagesResponse(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<ImageResponse>>() {
                }.getType());
    }

    public AbstractList<ContainerResponse> containersResponse(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<ContainerResponse>>() {
                }.getType());
    }

    public AbstractList<NetworkResponse> networksResponse(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<NetworkResponse>>() {
                }.getType());
    }

    public AbstractList<VolumeResponse> volumeResponse(final String json) {
        Preconditions.checkNotNull(json);

        return gson.fromJson(
                json,
                new TypeToken<Collection<VolumeResponse>>() {
                }.getType());
    }

    public ContainerCreateResponse containerCreateResponse(final Response response) throws IOException {
        Preconditions.checkNotNull(response);

        return gson.fromJson(response.body().string(), ContainerCreateResponse.class);
    }
}
