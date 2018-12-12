package unit.docker.utils;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.VolumeResponse;
import com.github.jameshnsears.docker.utils.RequestMapper;
import com.github.jameshnsears.docker.utils.ResponseMapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import unit.GsonCommon;
import unit.docker.ConfigurationAccessorParameterResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ExtendWith(ConfigurationAccessorParameterResolver.class)
public class RequestMapperTest extends GsonCommon {
    @Test
    void containerCreateRequest(ConfigurationAccessor configurationAccessor) throws IOException {
        RequestMapper requestMapper = new RequestMapper();
        final List<Configuration> configurationContainers = (List) configurationAccessor.containers();

        Assertions.assertEquals(
                FileUtils.readFileToString(
                        new File(getClass().getClassLoader().getResource("fixtures/docker/containerCreateRequest.json").getFile()), "UTF-8"),
                requestMapper.containerCreateRequest(configurationContainers.get(0)));
    }

}
