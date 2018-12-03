package unit.docker.models;

import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.models.Network;
import com.github.jameshnsears.docker.models.Volume;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import unit.GsonCommon;

import java.lang.reflect.Type;
import java.util.Collection;

public class ModelsTest extends GsonCommon {
    @Test
    public void lsImages() {
        Type collectionType = new TypeToken<Collection<Image>>() {
        }.getType();
        Collection<Image> images = gson.fromJson(getInputStreamReader("/fixtures/docker/images.json"), collectionType);

        Assert.assertEquals(
                images.size(),
                12);
    }

    @Test
    public void lsContainers() {
        Type collectionType = new TypeToken<Collection<Container>>() {
        }.getType();
        Collection<Container> containers = gson.fromJson(getInputStreamReader("/fixtures/docker/containers.json"), collectionType);

        Assert.assertEquals(
                containers.size(),
                9);
        Assert.assertEquals(
                ((Container) ((java.util.ArrayList) containers).get(0)).getId(),
                "sha256:7466ebd20c18d0a9decfba4a12b0f8c4cce87e38866b512ffd6d483bfb4c24e1");
        Assert.assertEquals(
                ((java.util.ArrayList) ((Container) ((java.util.ArrayList) containers).get(0)).getRepoTags()).get(0),
                "jameshnsears/xqa-shard:latest");
    }

    @Test
    public void lsNetworks() {
        Type collectionType = new TypeToken<Collection<Network>>() {
        }.getType();
        Collection<Network> networks = gson.fromJson(getInputStreamReader("/fixtures/docker/networks.json"), collectionType);

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
