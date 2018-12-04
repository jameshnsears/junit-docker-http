package com.github.jameshnsears;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ConfigurationAccessor {
    private Collection<Configuration> configurationCollection;

    public ConfigurationAccessor(Collection<Configuration> configurationCollection) {
        Preconditions.checkArgument(configurationCollection != null);
        this.configurationCollection = configurationCollection;
    }

    public ArrayList<String> images() {
        ArrayList<String> images = new ArrayList<>();
        for (Configuration configuration: this.configurationCollection) {
            images.add(configuration.getImage());
        }
        Collections.sort(images);
        return images;
    }

    public void containers() {
        /*
        def containers(self):
                return self.configurationCollection

        def networks(self):
        networks = []
                for configuration in self.configurationCollection:
                try:
                if configuration['network'] not in networks:
                networks.append(configuration['network'])
        except KeyError:
            pass
        return sorted(networks)
        */
    }

    public void volumes() {
        /*
        def volumes(self):
        volumes = []
                for configuration in self.configurationCollection:
                try:
                for image_volume in configuration['volumes']:
                if image_volume not in volumes:
                volumes.append(image_volume)
        except KeyError:
        pass
        return sorted(volumes)
        */
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
