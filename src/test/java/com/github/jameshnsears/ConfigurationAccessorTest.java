package com.github.jameshnsears;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.reflect.TypeToken;

class ConfigurationAccessorTest extends GsonCommon {
    private final ConfigurationAccessor configurationAccessor;

    ConfigurationAccessorTest() {
        final Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();

        configurationAccessor = new ConfigurationAccessor(gson.fromJson(getInputStreamReader("/fixtures/config.json"), collectionType));
    }

    @Test
    void images() {
        final ArrayList<String> expectation = new ArrayList<>();
        expectation.add("alpine:latest");
        expectation.add("busybox:latest");
        Assertions.assertArrayEquals(configurationAccessor.images().toArray(), expectation.toArray());
    }

    @Test
    void networks() {
        Assertions.assertArrayEquals(configurationAccessor.networks().toArray(), new String[]{"dev"});
    }

    @Test
    void volumes() {
        final ArrayList<Map<String, Map<String, String>>> volumes = new ArrayList<>();
        final Map<String, Map<String, String>> volume = new ConcurrentHashMap<>();

        final Map<String, String> volumeBindings = new ConcurrentHashMap<>();
        volumeBindings.put("bind", "/tmp");
        volumeBindings.put("mode", "/rw");

        volume.put("alpine-01", volumeBindings);

        volumes.add(volume);

        Assertions.assertEquals(
                configurationAccessor.volumes().size(),
                volumes.size());
    }
}
