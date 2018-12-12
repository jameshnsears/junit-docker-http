package unit.docker.utils;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.utils.ModelMapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import unit.docker.ConfigurationAccessorParameterResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ExtendWith(ConfigurationAccessorParameterResolver.class)

public class ModelMapperTest {
    @Test
    void jsonForStartingContainer(ConfigurationAccessor configurationAccessor) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        final List<Configuration> configurationContainers = (List) configurationAccessor.containers();

        File file = new File(getClass().getClassLoader().getResource("fixtures/configurationContainer.json").getFile());

        Assertions.assertEquals(
                FileUtils.readFileToString(file, "UTF-8"),
                modelMapper.mapConfigurationContainerIntoJson(configurationContainers.get(0)));
    }
}
