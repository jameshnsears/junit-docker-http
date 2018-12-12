package unit.docker.utils;

import com.github.jameshnsears.docker.models.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.GsonCommon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

class ResponseMapperTest extends GsonCommon {
    @Test
    void imagesResponse() {
        final ArrayList<ImageResponse> images = gson.fromJson(
                getInputStreamReader("/fixtures/docker/imagesResponse.json"),
                new TypeToken<Collection<ImageResponse>>() {
                }.getType());

        Assertions.assertEquals(
                images.size(),
                12);
    }

    @Test
    void containersResponse() {
        final ArrayList<ContainerResponse> containers = gson.fromJson(
                getInputStreamReader("/fixtures/docker/containersResponse.json"),
                new TypeToken<Collection<ContainerResponse>>() {
                }.getType());

        Assertions.assertEquals(
                containers.size(),
                7);
        Assertions.assertEquals(
                ((ContainerResponse) ((ArrayList) containers).get(0)).getId(),
                "60cb5bc7fa7f5ac26a48520f8bd68decd8307af1b114f25b29827120ba4d4c16");
        Assertions.assertEquals(
                ((ArrayList) ((ContainerResponse) ((ArrayList) containers).get(0)).getNames()).get(0),
                "/xqa-ingest");
    }

    @Test
    void networksResponse() {
        final ArrayList<NetworkResponse> networks = gson.fromJson(
                getInputStreamReader("/fixtures/docker/networksResponse.json"),
                new TypeToken<Collection<NetworkResponse>>() {
                }.getType());

        Assertions.assertEquals(
                networks.size(),
                4);
    }

    @Test
    void volumesResponse() throws IOException {
        final VolumeResponse volume = gson.fromJson(getInputStreamReader("/fixtures/docker/volumesResponse.json"), VolumeResponse.class);

        Assertions.assertEquals(
                volume.getVolumes().size(),
                3);
    }

    @Test
    void containerCreateResponse() {
        final ContainerCreateResponse containerCreateResponse = gson.fromJson(getInputStreamReader(
                "/fixtures/docker/containerCreateResponse.json"),
                ContainerCreateResponse.class);

        Assertions.assertEquals(
                containerCreateResponse.id,
                "d823d57b5cc63c0e72cac32c4049e43eaab8f52eef5af2ef6aa053d6838f28d2");
    }
}
