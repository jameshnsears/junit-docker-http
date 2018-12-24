package com.github.jameshnsears.docker.utils;

import com.github.jameshnsears.GsonCommon;
import com.github.jameshnsears.configuration.Configuration;
import com.github.jameshnsears.configuration.ConfigurationAccessor;
import com.github.jameshnsears.configuration.ConfigurationParameterResolver;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ExtendWith(ConfigurationParameterResolver.class)
class RequestMapperTest extends GsonCommon {
    @Test
    void containerCreateRequest(ConfigurationAccessor configurationAccessor) throws IOException {
        final RequestMapper requestMapper = new RequestMapper();
        final List<Configuration> configurationContainers = (List) configurationAccessor.containers();

        Assertions.assertEquals(
                FileUtils.readFileToString(
                        new File(Thread.currentThread().getContextClassLoader().getResource("fixtures/docker/containerCreateRequest.json").getFile()), "UTF-8"),
                requestMapper.containerCreateRequest(configurationContainers.get(0)));
    }

}