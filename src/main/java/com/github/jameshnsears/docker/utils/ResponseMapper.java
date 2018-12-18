package com.github.jameshnsears.docker.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.github.jameshnsears.docker.models.ContainerCreateResponse;
import com.github.jameshnsears.docker.models.ContainerResponse;
import com.github.jameshnsears.docker.models.ImageResponse;
import com.github.jameshnsears.docker.models.NetworkResponse;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Response;

public class ResponseMapper {
    private final Gson gson = new Gson();

    public ArrayList<ImageResponse> imagesResponse(final String json) {
        Preconditions.checkNotNull(json);
        return gson.fromJson(
                json,
                new TypeToken<Collection<ImageResponse>>() {
                }.getType());
    }

    public ArrayList<ContainerResponse> containersResponse(final String json) {
        Preconditions.checkNotNull(json);
        return gson.fromJson(
                json,
                new TypeToken<Collection<ContainerResponse>>() {
                }.getType());
    }

    public ArrayList<NetworkResponse> networksResponse(final String json) {
        Preconditions.checkNotNull(json);
        return gson.fromJson(
                json,
                new TypeToken<Collection<NetworkResponse>>() {
                }.getType());
    }

    public Map<String, List<Map<String, Object>>> volumeResponse(final String json) {
        Preconditions.checkNotNull(json);
        return gson.fromJson(json, new TypeToken<Map<String, List<Map<String, Object>>>>() {
        }.getType());
    }

    public ContainerCreateResponse containerCreateResponse(final Response response) throws IOException {
        Preconditions.checkNotNull(response);
        return gson.fromJson(response.body().string(), ContainerCreateResponse.class);
    }
}
