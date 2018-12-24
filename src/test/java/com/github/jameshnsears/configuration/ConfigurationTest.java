package com.github.jameshnsears.configuration;

import com.github.jameshnsears.GsonCommon;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class ConfigurationTest extends GsonCommon {
    @Test
    void loadConfig() {
        final Collection<Configuration> configurationCollection = gson.fromJson(
                getInputStreamReader("/fixtures/config.json"),
                new TypeToken<Collection<Configuration>>() {
                }.getType());

        Assertions.assertEquals(
                configurationCollection.size(),
                2);

        final Configuration configuration = (Configuration) ((ArrayList) configurationCollection).get(0);
        final Map<String, Integer> portMap = configuration.ports;
        Assertions.assertEquals(
                portMap.get("1234/tcp").intValue(),
                1234);
    }
}
