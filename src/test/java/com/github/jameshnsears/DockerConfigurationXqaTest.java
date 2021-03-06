package com.github.jameshnsears;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.jameshnsears.configuration.ConfigurationAccessor;
import com.github.jameshnsears.configuration.ConfigurationParameterResolverXqa;
import com.github.jameshnsears.docker.DockerClient;

@ExtendWith(ConfigurationParameterResolverXqa.class)
public class DockerConfigurationXqaTest {
    @Test
    public void testXqa(final ConfigurationAccessor configurationAccessor) throws IOException, InterruptedException {
        final DockerClient dockerClient = new DockerClient();
        dockerClient.pull(configurationAccessor.images());
        dockerClient.startContainers(configurationAccessor);

        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("xqa"));

        dockerClient.rmContainers(configurationAccessor);
    }
}
