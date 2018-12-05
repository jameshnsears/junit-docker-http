package unit.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.DockerClient;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
public class DockerClientTest {
    DockerClient dockerClient = new DockerClient();

    @Test
    public void pullImages(ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.rmImages(configurationAccessor.images());
        ArrayList<String> dockerImages = dockerClient.lsImages();
        for (String image: configurationAccessor.images())
            Assert.assertFalse(dockerImages.contains(image));


        dockerClient.pull(configurationAccessor.images());
        dockerImages = dockerClient.lsImages();
        for (String image: configurationAccessor.images())
            Assert.assertTrue(dockerImages.contains(image));
    }

    @Test
    public void stopStartContainers(ConfigurationAccessor configurationAccessor) {
//        ArrayList<String> images = dockerClient.lsImages();
//
//        ArrayList<Map<String, Object>> containers = dockerClient.lsContainers(configurationAccessor);



        /*
        docker_py_wrapper.start_containers(configuration)
        assert len(docker_py_wrapper.ls_containers(configuration.images())) == 2
        assert docker_py_wrapper.ls_networks(configuration.networks()) == ['docker_py_wrapper']
        assert docker_py_wrapper.ls_volumes(configuration.volumes()) == ['alpine-01:/tmp']

        docker_py_wrapper.rm_containers(configuration.images())
        assert docker_py_wrapper.ls_containers(configuration.images()) == []
        assert docker_py_wrapper.ls_networks(configuration.networks()) == []
        assert docker_py_wrapper.ls_volumes(configuration.volumes()) == []
         */
    }
}
