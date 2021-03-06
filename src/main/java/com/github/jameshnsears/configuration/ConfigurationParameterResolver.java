package com.github.jameshnsears.configuration;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConfigurationParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ConfigurationAccessor.class;
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {

        final Gson gson = new Gson();
        final Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();
        final Collection<Configuration> configurationCollection = gson.fromJson(getInputStreamReader(), collectionType);

        return new ConfigurationAccessor(configurationCollection);
    }

    protected InputStreamReader getInputStreamReader() {
        return new InputStreamReader(getClass().getResourceAsStream("/config.json"));
    }
}
