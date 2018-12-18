package unit.docker;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.jameshnsears.ConfigurationAccessor;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
class DockerClientTest extends DockerClientBaseTest {
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
            // give travis-ci time to start containers
            Thread.sleep(30000);
        }

        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("dev"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("alpine-01"));

        assertConfigurationContainersRemoved(configurationAccessor);
    }
}
