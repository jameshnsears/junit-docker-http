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

class DockerClientBaseTest {
    protected final DockerClient dockerClient = new DockerClient();

    protected void assertConfigurationImagesNotPulled(ConfigurationAccessor configurationAccessor) throws IOException {
        ArrayList<String> configurationImages = configurationAccessor.images();

        dockerClient.rmImages(configurationImages);

        List<Map<String, String>> dockerImages = dockerClient.lsImages();
        for (final String image : configurationImages) {
            for (Map<String, String> dockerImage : dockerImages) {
                Assertions.assertFalse(dockerImage.get("name").equals(image));
            }
        }
    }

    protected void assertConfigurationImagesPulled(ConfigurationAccessor configurationAccessor) throws IOException {
        ArrayList<String> configurationImages = configurationAccessor.images();
        dockerClient.pull(configurationImages);
        List<Map<String, String>> dockerImages = dockerClient.lsImages();
        List<String> dockerImageNames = new ArrayList<>();
        for (Map<String, String> dockerImage : dockerImages) {
            dockerImageNames.add(dockerImage.get("name"));
        }
        Collections.sort(dockerImageNames);
        Assertions.assertArrayEquals(dockerImageNames.toArray(), configurationAccessor.images().toArray());
    }

    protected void assertConfigurationContainersRemoved(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);
    }

    protected void assertConfigurationContainersStarted(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("dev"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("alpine-01"));
    }
}
