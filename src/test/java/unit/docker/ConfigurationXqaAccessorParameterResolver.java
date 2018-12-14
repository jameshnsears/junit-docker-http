package unit.docker;

import java.io.InputStreamReader;

public class ConfigurationXqaAccessorParameterResolver extends ConfigurationAccessorParameterResolver {
    protected InputStreamReader getInputStreamReader() {
        return new InputStreamReader(getClass().getResourceAsStream("/fixtures/config-xqa.json"));
    }
}
