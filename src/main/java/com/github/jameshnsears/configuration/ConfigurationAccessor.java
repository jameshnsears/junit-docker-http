package com.github.jameshnsears.configuration;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Preconditions;

public class ConfigurationAccessor {
    private final Collection<Configuration> configurationCollection;

    public ConfigurationAccessor(final Collection<Configuration> configurationCollection) {
        Preconditions.checkNotNull(configurationCollection);

        this.configurationCollection = configurationCollection;
    }

    public ArrayList<String> images() {
        final ArrayList<String> images = new ArrayList<>();
        for (final Configuration configuration : this.configurationCollection) {
            if (!configuration.image.isEmpty() && !images.contains(configuration.image)) {
                images.add(configuration.image);
            }
        }
        Collections.sort(images);
        return images;
    }

    public AbstractList<String> imageNames() {
        final ArrayList<String> imageNames = new ArrayList<>();
        for (final Configuration configuration : this.configurationCollection) {
            if (!configuration.name.isEmpty()) {
                imageNames.add(configuration.name);
            }
        }
        Collections.sort(imageNames);
        return imageNames;
    }

    public Collection<Configuration> containers() {
        return this.configurationCollection;
    }

    public ArrayList<String> networks() {
        final ArrayList<String> networks = new ArrayList<>();
        for (final Configuration configuration : this.configurationCollection) {
            if (!configuration.network.isEmpty()) {
                networks.add(configuration.network);
            }
        }
        Collections.sort(networks);
        return networks;
    }

    public ArrayList<String> volumes() {
        final ArrayList<String> volumes = new ArrayList<>();
        for (final Configuration configuration : this.configurationCollection) {
            for (final Map.Entry<String, Map<String, String>> volume : configuration.volumes.entrySet()) {
                volumes.add(volume.getKey());
            }
        }
        Collections.sort(volumes);
        return volumes;
    }
}
