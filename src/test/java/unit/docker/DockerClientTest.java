package unit.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.DockerClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
class DockerClientTest {
    private final DockerClient dockerClient = new DockerClient();

    @Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmImages(configurationAccessor.images());
        List<Map<String, String>> dockerImages = dockerClient.lsImages();
        for (final String image : configurationAccessor.images()) {
            for (Map<String, String> dockerImage : dockerImages) {
                Assertions.assertFalse(dockerImage.get("name").equals(image));
            }
        }

        dockerClient.pull(configurationAccessor.images());
        dockerImages = dockerClient.lsImages();
        List<String> dockerImageNames = new ArrayList<>();
        for (Map<String, String> dockerImage : dockerImages) {
            dockerImageNames.add(dockerImage.get("name"));
        }
        Collections.sort(dockerImageNames);
        Assertions.assertArrayEquals(dockerImageNames.toArray(), configurationAccessor.images().toArray());
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("dev"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("alpine-01"));

        dockerClient.rmContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);
    }
}
