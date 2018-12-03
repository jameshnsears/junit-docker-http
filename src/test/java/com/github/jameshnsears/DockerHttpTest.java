package com.github.jameshnsears;

import com.github.jameshnsears.model.Container;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.client.ApiException;
import io.swagger.client.api.ContainerApi;
import io.swagger.client.api.ImageApi;
import io.swagger.client.model.ContainerSummary;
import io.swagger.client.model.ImageSummary;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class DockerHttpTest {
    private static final Logger log = LoggerFactory.getLogger(DockerHttp.class);

    @Test
    public void lsContainers() throws Exception {
        // TODO: test validations
        DockerHttp dockerHttp = new DockerHttp();
//        String jsonResponse = dockerHttp.lsContainers();

        Gson gson = new Gson();
//        Container container = gson.fromJson(jsonResponse, Container.class);
//        Container container = gson.fromJson(new InputStreamReader(is), Container.class);

        InputStream is = getClass().getResourceAsStream("/fixtures/lsContainers.json");

        Type collectionType = new TypeToken<Collection<Container>>(){}.getType();
        Collection<Container> containers = gson.fromJson(new InputStreamReader(is), collectionType);


        Assert.assertEquals("", "");
    }
}
