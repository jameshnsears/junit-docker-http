package unit.docker;

import com.github.jameshnsears.docker.DockerHttpWrapper;
import com.github.jameshnsears.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

@ExtendWith(ConfigParameterResolver.class)
public class DockerHttpWrapperTest {
    DockerHttpWrapper dockerHttp = new DockerHttpWrapper();

    @Test
    public void configuration(Config config) {
        System.out.println(config.a);
        /*
        assert configuration.images() == ['alpine:latest', 'busybox:latest']
        assert configuration.networks() == ['docker_py_wrapper']
        */
    }


    @Test
    public void pullImages(Config config) {
        /*
        docker_py_wrapper.rm_images(configuration.images())
        for configuration_image in configuration.images():
            assert configuration_image not in docker_py_wrapper.ls_images()

        docker_py_wrapper.pull(configuration.images())
        for configuration_image in configuration.images():
            assert configuration_image in docker_py_wrapper.ls_images()
         */
    }

    @Test
    public void stopStartContainers(Config config) {
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

    @Test
    public void lsImages() throws IOException {
        String jsonResponse = dockerHttp.lsImages();
    }

    @Test
    public void lsContainers() throws IOException {
        String jsonResponse = dockerHttp.lsContainers();
    }
}
