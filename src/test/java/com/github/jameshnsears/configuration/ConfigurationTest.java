package com.github.jameshnsears.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.jameshnsears.GsonCommon;
import com.google.gson.reflect.TypeToken;

class ConfigurationTest extends GsonCommon {
    @Test
    public void loadConfig() {
        final Collection<Configuration> configurationCollection = gson.fromJson(
                getInputStreamReader("/config.json"),
                new TypeToken<Collection<Configuration>>() {
                }.getType());

        Assertions.assertEquals(
                configurationCollection.size(),
                2);

        final Configuration configuration = (Configuration) ((ArrayList<Configuration>) configurationCollection).get(0);
        final Map<String, Integer> portMap = configuration.ports;
        Assertions.assertEquals(
                portMap.get("1234/tcp").intValue(),
                1234);
    }
}
