package com.github.jameshnsears;

import io.swagger.client.ApiException;
import io.swagger.client.api.ContainerApi;
import io.swagger.client.api.ImageApi;
import io.swagger.client.model.ContainerSummary;
import io.swagger.client.model.ImageSummary;
import org.junit.Test;

import java.util.List;

public class DockerHttpTest {
    private final ContainerApi api = new ContainerApi();

    @Test
    public void containerListTest() throws ApiException {
        // TODO: test validations
        return;
    }
}
