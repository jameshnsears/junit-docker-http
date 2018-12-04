package unit;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class ConfigurationAccessorTest extends GsonCommon {
    @Test
    public void testConfigurationAccessor() {
        Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();
        Collection<Configuration> configurationCollection = gson.fromJson(getInputStreamReader("/fixtures/config.json"), collectionType);

        ConfigurationAccessor configurationAccessor = new ConfigurationAccessor(configurationCollection);

        ArrayList<String> expectation = new ArrayList<>();
        expectation.add("alpine:latest");
        expectation.add("busybox:latest");
        Assert.assertArrayEquals(configurationAccessor.images().toArray(), expectation.toArray());
    }
}
