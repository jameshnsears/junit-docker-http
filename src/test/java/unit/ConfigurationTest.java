package unit;

import com.github.jameshnsears.Configuration;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ConfigurationTest extends GsonCommon {
    @Test
    public void loadConfig() {
        Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();
        Collection<Configuration> configurationCollection = gson.fromJson(getInputStreamReader("/fixtures/config.json"), collectionType);

        Assert.assertEquals(
                configurationCollection.size(),
                2);

        Configuration configuration = (Configuration) ((ArrayList) configurationCollection).get(0);
        Map<String, Integer> portMap = configuration.getPorts();
        Assert.assertEquals(
                portMap.get("1234/tcp").intValue(),
                1234);
    }
}
