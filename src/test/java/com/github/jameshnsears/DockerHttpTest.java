package com.github.jameshnsears;

import com.github.jameshnsears.model.Container;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

public class DockerHttpTest {
    @Test
    public void lsContainersGson() throws Exception {
//        DockerHttp dockerHttp = new DockerHttp();
//        String jsonResponse = dockerHttp.lsContainers();
//        Container container = gson.fromJson(jsonResponse, Container.class);
//        Container container = gson.fromJson(new InputStreamReader(is), Container.class);

        Gson gson = new Gson();
        InputStream is = getClass().getResourceAsStream("/fixtures/lsContainers.json");
        Type collectionType = new TypeToken<Collection<Container>>() {
        }.getType();
        Collection<Container> containers = gson.fromJson(new InputStreamReader(is), collectionType);
        Assert.assertEquals(containers.size(), 9);
        Assert.assertEquals(((Container) ((java.util.ArrayList) containers).get(0)).getId(), "sha256:7466ebd20c18d0a9decfba4a12b0f8c4cce87e38866b512ffd6d483bfb4c24e1");
        Assert.assertEquals(((java.util.ArrayList) ((Container) ((java.util.ArrayList) containers).get(0)).getRepoTags()).get(0), "jameshnsears/xqa-shard:latest");
    }
}
