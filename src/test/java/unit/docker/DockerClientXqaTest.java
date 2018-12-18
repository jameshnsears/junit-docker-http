package unit.docker;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.jameshnsears.ConfigurationAccessor;

@ExtendWith(ConfigurationXqaAccessorParameterResolver.class)
class DockerClientXqaTest extends DockerClientBase {
    @Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        assertConfigurationImagesNotPulled(configurationAccessor);

        assertConfigurationImagesPulled(configurationAccessor);
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException, InterruptedException {
        dockerClient.startContainers(configurationAccessor);

        if (System.getenv().get("TRAVIS") != null) {
            Thread.sleep(15000);
        }

        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 7);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("xqa"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("xqa-ingest"));

        assertConfigurationContainersRemoved(configurationAccessor);
    }
}
