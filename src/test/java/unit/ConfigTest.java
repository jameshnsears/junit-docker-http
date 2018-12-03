package unit;

import com.github.jameshnsears.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ConfigTest {
    private final Gson gson;

    public ConfigTest() {
        gson = new Gson();
    }

    private InputStreamReader getInputStreamReader(String s) {
        return new InputStreamReader(getClass().getResourceAsStream(s));
    }

    @Test
    public void loadConfig() {
        Type collectionType = new TypeToken<Collection<Config>>() {
        }.getType();
        Collection<Config> configCollection = gson.fromJson(getInputStreamReader("/fixtures/config.json"), collectionType);

        Assert.assertEquals(
                configCollection.size(),
                2);

        Config config = (Config) ((ArrayList) configCollection).get(0);
        Map<String, Integer> portMap = config.getPorts();
        Assert.assertEquals(
                portMap.get("1234/tcp").intValue(),
                1234);
    }
}
