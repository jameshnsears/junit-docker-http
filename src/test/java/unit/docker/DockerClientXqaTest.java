package unit.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

@ExtendWith(ConfigurationXqaAccessorParameterResolver.class)
class DockerClientXqaTest extends DockerClientBaseTest {
    //@Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        assertConfigurationImagesNotPulled(configurationAccessor);

        assertConfigurationImagesPulled(configurationAccessor);
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        dockerClient.startContainers(configurationAccessor);
        Assertions.assertTrue(dockerClient.lsContainers(configurationAccessor).size() == 7);
        Assertions.assertTrue(dockerClient.lsNetworks(configurationAccessor).contains("xqa"));
        Assertions.assertTrue(dockerClient.lsVolumes(configurationAccessor).contains("/home/jsears/GIT_REPOS/xqa-test-data"));

        assertConfigurationContainersRemoved(configurationAccessor);
    }
}
