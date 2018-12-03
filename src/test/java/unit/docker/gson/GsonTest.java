package unit.docker.gson;

import com.github.jameshnsears.docker.gson.Container;
import com.github.jameshnsears.docker.gson.Image;
import com.github.jameshnsears.docker.gson.Network;
import com.github.jameshnsears.docker.gson.Volume;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

public class GsonTest {

    private final Gson gson;

    public GsonTest() {
        gson = new Gson();
    }

    @Test
    public void lsImagesGson() {
        Type collectionType = new TypeToken<Collection<Image>>() {
        }.getType();
        Collection<Image> images = gson.fromJson(getInputStreamReader("/fixtures/docker/images.json"), collectionType);

        Assert.assertEquals(
                images.size(),
                12);
    }

    private InputStreamReader getInputStreamReader(String s) {
        return new InputStreamReader(getClass().getResourceAsStream(s));
    }

    @Test
    public void lsContainersGson() {
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
    public void lsNetworksGson() {
        Type collectionType = new TypeToken<Collection<Network>>() {
        }.getType();
        Collection<Network> networks = gson.fromJson(getInputStreamReader("/fixtures/docker/networks.json"), collectionType);

        Assert.assertEquals(
                networks.size(),
                4);
    }

    @Test
    public void lsVolumesGson() {
        Volume volume = gson.fromJson(getInputStreamReader("/fixtures/docker/volumes.json"), Volume.class);

        Assert.assertEquals(
                volume.getVolumes().size(),
                3);
    }
}
