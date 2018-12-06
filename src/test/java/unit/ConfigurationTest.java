package unit;

import com.github.jameshnsears.Configuration;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class ConfigurationTest extends GsonCommon {
    @Test
    void loadConfig() {
        Collection<Configuration> configurationCollection = gson.fromJson(
                getInputStreamReader("/fixtures/config.json"),
                new TypeToken<Collection<Configuration>>() {
                }.getType());

        Assertions.assertEquals(
                configurationCollection.size(),
                2);

        Configuration configuration = (Configuration) ((ArrayList) configurationCollection).get(0);
        Map<String, Integer> portMap = configuration.getPorts();
        Assertions.assertEquals(
                portMap.get("1234/tcp").intValue(),
                1234);
    }
}
