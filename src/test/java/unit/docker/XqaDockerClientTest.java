package unit.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(ConfigurationXqaAccessorParameterResolver.class)
class XqaDockerClientTest extends DockerClientBaseTest {
    @Test
    void pullImages(final ConfigurationAccessor configurationAccessor) throws IOException {
        assertConfigurationImagesNotPulled(configurationAccessor);

        assertConfigurationImagesPulled(configurationAccessor);
    }

    @Test
    void stopStartContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        assertConfigurationContainersStarted(configurationAccessor);

        assertConfigurationContainersRemoved(configurationAccessor);
    }
}
