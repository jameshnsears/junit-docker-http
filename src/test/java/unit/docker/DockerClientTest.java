package unit.docker;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.DockerClient;
import com.github.jameshnsears.docker.utils.ModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
class DockerClientTest {
    private final DockerClient dockerClient = new DockerClient();

    //@Test
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
    void jsonForStartingContainer(ConfigurationAccessor configurationAccessor) {
        ModelMapper modelMapper = new ModelMapper();
        final List<Configuration> configurationContainers = (List) configurationAccessor.containers();
        String json = modelMapper.mapConfigurationContainerIntoJson(configurationContainers.get(0));

        String expectedJson = "";
        /*
        {
"ExposedPorts": {"1234/tcp": {}},
"Tty": false,
"OpenStdin": false,
"StdinOnce": false,
"AttachStdin": false,
"AttachStdout": false,
"AttachStderr": false,
"Cmd": ["sleep", "12345"],
"Image": "alpine:latest",
"Volumes": {"/tmp": {}},
"NetworkDisabled": false,
"HostConfig": {
    "NetworkMode": "default",
    "Binds": ["alpine-01:/tmp:rw"],
    "PortBindings": {
        "1234/tcp": [{
            "HostIp": "", "HostPort": "1234"}
            ]}
        }
    }
         */
        Assertions.assertEquals( expectedJson, json);
    }

    //@Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
//        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 2);
//        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == ['docker_py_wrapper');
//        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == ['alpine-01:/tmp');
//
        dockerClient.rmContainers(configurationAccessor);
        Assertions.fail();
//        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 0);
//        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).size() == 0);
//        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).size() == 0);
    }
}
