package unit.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.DockerClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
class DockerClientTest {
    private final DockerClient dockerClient = new DockerClient();

    //    @Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmImages(configurationAccessor.images());
        ArrayList<String> dockerImages = (ArrayList) dockerClient.lsImages();
        for (final String image : configurationAccessor.images()) {
            Assertions.assertFalse(dockerImages.contains(image));
        }

        dockerClient.pull(configurationAccessor.images());
        dockerImages = (ArrayList) dockerClient.lsImages();
        for (final String image : configurationAccessor.images()) {
            Assertions.assertTrue(dockerImages.contains(image));
        }
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
        Assertions.assertTrue(dockerClient.lsNetworks().contains("dev"));
        Assertions.assertTrue(dockerClient.lsVolumes().contains("alpine-01:/tmp"));
//
        dockerClient.rmContainers(configurationAccessor);
//        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
//        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
//        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);


        Assertions.fail();
    }
}
