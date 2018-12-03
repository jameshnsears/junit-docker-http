package com.github.jameshnsears;

import org.junit.Test;

public class DockerHttpTest {
    @Test
    public void lsImages() {

    }

    @Test
    public void lsContainers() throws Exception {
        DockerHttp dockerHttp = new DockerHttp();
        String jsonResponse = dockerHttp.lsContainers();
    }
}
