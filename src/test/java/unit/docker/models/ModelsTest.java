package unit.docker.models;

import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.models.Network;
import com.github.jameshnsears.docker.models.Volume;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import unit.GsonCommon;

import java.util.ArrayList;
import java.util.Collection;

public class ModelsTest extends GsonCommon {
    @Test
    public void lsImages() {
        ArrayList<Image> images = gson.fromJson(
                getInputStreamReader("/fixtures/docker/images.json"),
                new TypeToken<Collection<Image>>() {
                }.getType());

        Assert.assertEquals(
                images.size(),
                12);
    }

    @Test
    public void lsContainers() {
        ArrayList<Container> containers = gson.fromJson(
                getInputStreamReader("/fixtures/docker/containers.json"),
                new TypeToken<Collection<Container>>() {
                }.getType());

        Assert.assertEquals(
                containers.size(),
                7);
        Assert.assertEquals(
                ((Container) ((java.util.ArrayList) containers).get(0)).getId(),
                "60cb5bc7fa7f5ac26a48520f8bd68decd8307af1b114f25b29827120ba4d4c16");
        Assert.assertEquals(
                ((java.util.ArrayList) ((Container) ((java.util.ArrayList) containers).get(0)).getNames()).get(0),
                "/xqa-ingest");
    }

    @Test
    public void lsNetworks() {
        ArrayList<Network> networks = gson.fromJson(
                getInputStreamReader("/fixtures/docker/networks.json"),
                new TypeToken<Collection<Network>>() {
                }.getType());

        Assert.assertEquals(
                networks.size(),
                4);
    }

    @Test
    public void lsVolumes() {
        Volume volume = gson.fromJson(getInputStreamReader("/fixtures/docker/volumes.json"), Volume.class);

        Assert.assertEquals(
                volume.getVolumes().size(),
                3);
    }
}
