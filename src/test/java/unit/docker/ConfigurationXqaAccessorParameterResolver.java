package unit.docker;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

public class ConfigurationXqaAccessorParameterResolver extends ConfigurationAccessorParameterResolver {
    protected InputStreamReader getInputStreamReader() {
        return new InputStreamReader(getClass().getResourceAsStream("/fixtures/config-xqa.json"));
    }
}
