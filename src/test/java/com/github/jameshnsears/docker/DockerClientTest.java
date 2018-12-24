package com.github.jameshnsears.docker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.jameshnsears.ConfigurationAccessor;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
class DockerClientTest {
    protected final DockerClient dockerClient = new DockerClient();

    protected void assertConfigurationImagesNotPulled(ConfigurationAccessor configurationAccessor) throws IOException {
        final ArrayList<String> configurationImages = configurationAccessor.images();

        dockerClient.rmImages(configurationImages);

        final List<String> dockerImageNames = getDockerImageNames();

        for (final String configurationImage : configurationImages) {
            Assertions.assertFalse(dockerImageNames.contains(configurationImage));
        }
    }

    private List<String> getDockerImageNames() throws IOException {
        final List<Map<String, String>> dockerImages = dockerClient.lsImages();

        final List<String> dockerImageNames = new ArrayList<>();
        for (final Map<String, String> dockerImage : dockerImages) {
            dockerImageNames.add(dockerImage.get("name"));
        }
        return dockerImageNames;
    }

    protected void assertConfigurationImagesPulled(ConfigurationAccessor configurationAccessor) throws IOException {
        final ArrayList<String> configurationImages = configurationAccessor.images();
        dockerClient.pull(configurationImages);

        final List<String> dockerImageNames = getDockerImageNames();

        for (final String configurationImage : configurationImages) {
            Assertions.assertTrue(dockerImageNames.contains(configurationImage));
        }
    }

    protected void assertConfigurationContainersRemoved(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);
    }

    @Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        assertConfigurationImagesNotPulled(configurationAccessor);
        assertConfigurationImagesPulled(configurationAccessor);
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor)
            throws IOException, InterruptedException {
        dockerClient.startContainers(configurationAccessor);

        if (System.getenv().get("TRAVIS") != null) {
            Thread.sleep(5000);
        }

        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("dev"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("alpine-01"));

        assertConfigurationContainersRemoved(configurationAccessor);
    }
}
