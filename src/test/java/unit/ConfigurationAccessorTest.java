package unit;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationAccessorTest extends GsonCommon {
    private ConfigurationAccessor configurationAccessor;

    public ConfigurationAccessorTest() {
        Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();

        configurationAccessor = new ConfigurationAccessor(gson.fromJson(getInputStreamReader("/fixtures/config.json"), collectionType));
    }

    @Test
    public void images() {
        ArrayList<String> expectation = new ArrayList<>();
        expectation.add("alpine:latest");
        expectation.add("busybox:latest");
        Assert.assertArrayEquals(configurationAccessor.images().toArray(), expectation.toArray());
    }

    @Test
    public void networks() {
        Assert.assertArrayEquals(configurationAccessor.networks().toArray(), new String[] { "dev" });
    }

    @Test
    public void volumes() {
        ArrayList<Map<String, Map<String, String>>> volumes = new ArrayList<>();
        Map<String, Map<String, String>> volume = new HashMap<>();

        Map<String, String> volumeBindings = new HashMap<>();
        volumeBindings.put("bind", "/tmp");
        volumeBindings.put("mode", "/rw");

        volume.put("alpine-01", volumeBindings);

        volumes.add(volume);

        Assert.assertEquals(
                configurationAccessor.volumes().size(),
                volumes.size());
    }
}
