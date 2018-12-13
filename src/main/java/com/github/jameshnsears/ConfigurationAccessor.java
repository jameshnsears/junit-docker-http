package com.github.jameshnsears;

import com.google.common.base.Preconditions;

import java.util.*;

public class ConfigurationAccessor {
    private final Collection<Configuration> configurationCollection;

    public ConfigurationAccessor(final Collection<Configuration> configurationCollection) {
        Preconditions.checkNotNull(configurationCollection);

        this.configurationCollection = configurationCollection;
    }

    public ArrayList<String> images() {
        final ArrayList<String> images = new ArrayList<>();
        for (Configuration configuration : this.configurationCollection) {
            if (!configuration.image.isEmpty()) {
                images.add(configuration.image);
            }
        }
        Collections.sort(images);
        return images;
    }

    public AbstractList<String> imageNames() {
        final ArrayList<String> imageNames = new ArrayList<>();
        for (Configuration configuration : this.configurationCollection) {
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

    public ArrayList<Map<String, Map<String, String>>> volumes() {
        final ArrayList<Map<String, Map<String, String>>> volumes = new ArrayList<>();
        for (final Configuration configuration : this.configurationCollection) {
            if (!configuration.volumes.isEmpty()) {
                volumes.add(configuration.volumes);
            }
        }
        return volumes;
    }
}
