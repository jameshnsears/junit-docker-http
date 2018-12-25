package com.github.jameshnsears.docker.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.jameshnsears.GsonCommon;
import com.github.jameshnsears.docker.models.ContainerCreateResponse;
import com.github.jameshnsears.docker.models.ContainerResponse;
import com.github.jameshnsears.docker.models.ImageResponse;
import com.github.jameshnsears.docker.models.NetworkResponse;
import com.google.gson.reflect.TypeToken;

class ResponseMapperTest extends GsonCommon {
    @Test
    public void imagesResponse() {
        final ArrayList<ImageResponse> images = gson.fromJson(
                getInputStreamReader("/fixtures/docker/imagesResponse.json"),
                new TypeToken<Collection<ImageResponse>>() {
                }.getType());

        Assertions.assertEquals(
                images.size(),
                12);
    }

    @Test
    public void containersResponse() {
        final ArrayList<ContainerResponse> containers = gson.fromJson(
                getInputStreamReader("/fixtures/docker/containersResponse.json"),
                new TypeToken<Collection<ContainerResponse>>() {
                }.getType());

        Assertions.assertEquals(
                containers.size(),
                7);
        Assertions.assertEquals(
                ((ContainerResponse) ((ArrayList) containers).get(0)).id,
                "60cb5bc7fa7f5ac26a48520f8bd68decd8307af1b114f25b29827120ba4d4c16");
        Assertions.assertEquals(
                ((ArrayList) ((ContainerResponse) ((ArrayList) containers).get(0)).names).get(0),
                "/xqa-ingest");
    }

    @Test
    public void networksResponse() {
        final ArrayList<NetworkResponse> networks = gson.fromJson(
                getInputStreamReader("/fixtures/docker/networksResponse.json"),
                new TypeToken<Collection<NetworkResponse>>() {
                }.getType());

        Assertions.assertEquals(
                networks.size(),
                4);
    }

    @Test
    public void volumesResponse() throws IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String json = FileUtils.readFileToString(
                new File(classLoader.getResource("fixtures/docker/volumesResponse.json").getFile()), "UTF-8");

        final ResponseMapper responseMapper = new ResponseMapper();
        final Map<String, List<Map<String, Object>>> volumeResponse = responseMapper.volumeResponse(json);

        Assertions.assertEquals(volumeResponse.get("Volumes").size(), 3);
    }

    @Test
    public void containerCreateResponse() {
        final ContainerCreateResponse containerCreateResponse = gson.fromJson(getInputStreamReader(
                "/fixtures/docker/containerCreateResponse.json"),
                ContainerCreateResponse.class);

        Assertions.assertEquals(
                containerCreateResponse.id,
                "d823d57b5cc63c0e72cac32c4049e43eaab8f52eef5af2ef6aa053d6838f28d2");
    }
}
