package unit.docker.models;

import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.models.Network;
import com.github.jameshnsears.docker.models.Volume;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.GsonCommon;

import java.util.ArrayList;
import java.util.Collection;

class ModelsTest extends GsonCommon {
    @Test
    void lsImages() {
        final ArrayList<Image> images = gson.fromJson(
                getInputStreamReader("/fixtures/docker/images.json"),
                new TypeToken<Collection<Image>>() {
                }.getType());

        Assertions.assertEquals(
                images.size(),
                12);
    }

    @Test
    void lsContainers() {
        final ArrayList<Container> containers = gson.fromJson(
                getInputStreamReader("/fixtures/docker/containers.json"),
                new TypeToken<Collection<Container>>() {
                }.getType());

        Assertions.assertEquals(
                containers.size(),
                7);
        Assertions.assertEquals(
                ((Container) ((java.util.ArrayList) containers).get(0)).getId(),
                "60cb5bc7fa7f5ac26a48520f8bd68decd8307af1b114f25b29827120ba4d4c16");
        Assertions.assertEquals(
                ((java.util.ArrayList) ((Container) ((java.util.ArrayList) containers).get(0)).getNames()).get(0),
                "/xqa-ingest");
    }

    @Test
    void lsNetworks() {
        final ArrayList<Network> networks = gson.fromJson(
                getInputStreamReader("/fixtures/docker/networks.json"),
                new TypeToken<Collection<Network>>() {
                }.getType());

        Assertions.assertEquals(
                networks.size(),
                4);
    }

    @Test
    void lsVolumes() {
        final Volume volume = gson.fromJson(getInputStreamReader("/fixtures/docker/volumes.json"), Volume.class);

        Assertions.assertEquals(
                volume.getVolumes().size(),
                3);
    }
}
