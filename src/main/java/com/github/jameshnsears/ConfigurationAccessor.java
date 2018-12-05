package com.github.jameshnsears;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ConfigurationAccessor {
    private Collection<Configuration> configurationCollection;

    public ConfigurationAccessor(Collection<Configuration> configurationCollection) {
        Preconditions.checkNotNull(configurationCollection);
        this.configurationCollection = configurationCollection;
    }

    public ArrayList<String> images() {
        ArrayList<String> images = new ArrayList<>();
        for (Configuration configuration : this.configurationCollection) {
            if (configuration.getImage() != null)
                images.add(configuration.getImage());
        }
        Collections.sort(images);
        return images;
    }

    public Collection<Configuration> containers() {
        return this.configurationCollection;
    }

    public ArrayList<String> networks() {
        ArrayList<String> networks = new ArrayList<>();
        for (Configuration configuration : this.configurationCollection) {
            if (configuration.getNetwork() != null)
                networks.add(configuration.getNetwork());
        }
        Collections.sort(networks);
        return networks;
    }

    public ArrayList<Map<String, Map<String, String>>> volumes() {
        ArrayList<Map<String, Map<String, String>>> volumes = new ArrayList<>();
        for (Configuration configuration : this.configurationCollection) {
            if (configuration.getVolumes() != null) {
                volumes.add(configuration.getVolumes());
            }
        }
        return volumes;
    }

    public void containerKwargs() {
        /*
        def container_kwargs(self, container):
        container_dict = {}

        self._container_kwarg(container_dict, container, 'name')
                self._container_kwarg(container_dict, container, 'ports')
                self._container_kwarg(container_dict, container, 'network')
                self._container_kwarg(container_dict, container, 'volumes')
                self._container_kwarg(container_dict, container, 'command')
                self._container_kwarg(container_dict, container, 'environment')

                return container_dict
        */
    }

    private void _container_kwarg() {
        /*
        def _container_kwarg(self, container_dict, container, key):
                try:
        container_dict[key] = container[key]
        except KeyError:
                logging.warning('%s - missing: %s' % (container['image'], key))
        pass
        return container_dict
        */
    }

    public class ConfigException extends Exception {
        public ConfigException(String message) {
            super(message);
        }
    }
}
