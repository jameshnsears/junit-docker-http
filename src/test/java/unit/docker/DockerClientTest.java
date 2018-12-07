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

    //@Test
    void pullImages(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmImages(configurationAccessor.images());
        ArrayList<String> dockerImages = dockerClient.lsImages();
        for (String image : configurationAccessor.images())
            Assertions.assertFalse(dockerImages.contains(image));


        dockerClient.pull(configurationAccessor.images());
        dockerImages = dockerClient.lsImages();
        for (String image : configurationAccessor.images())
            Assertions.assertTrue(dockerImages.contains(image));
    }

    @Test
    void stopStartContainers(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
//        Assert.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
////        Assert.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == ['docker_py_wrapper');
////        Assert.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == ['alpine-01:/tmp');
//
        dockerClient.rmContainers(configurationAccessor);
//        Assert.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
//        Assert.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
//        Assert.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);
    }
}
