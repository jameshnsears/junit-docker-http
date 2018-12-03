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

public class ConfigurationAccessorParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ConfigurationAccessor.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {

        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();
        Collection<Configuration> configurationCollection = gson.fromJson(getInputStreamReader(), collectionType);

        return new ConfigurationAccessor(configurationCollection);
    }

    private InputStreamReader getInputStreamReader() {
        return new InputStreamReader(getClass().getResourceAsStream("/fixtures/config.json"));
    }
}
