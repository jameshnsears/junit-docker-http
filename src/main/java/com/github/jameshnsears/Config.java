package com.github.jameshnsears;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Config {
    public int a = 123;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ports")
    @Expose
    private Map<String, Integer> ports;
    @SerializedName("volumes")
    @Expose
    private List<String> volumes = null;
    @SerializedName("command")
    @Expose
    private String command;
    @SerializedName("network")
    @Expose
    private String network;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getPorts() {
        return ports;
    }

    public void setPorts(Map<String, Integer> ports) {
        this.ports = ports;
    }

    public List<String> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<String> volumes) {
        this.volumes = volumes;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    /*
    def __init__(self, configuration_from_fixture):
    self._configuration_from_fixture = configuration_from_fixture
    */

    /*
    def images(self):
    images = []
            for configuration in self._configuration_from_fixture:
            try:
            images.append(configuration['image'])
    except KeyError:
            logging.error('missing: image')
            return sorted(images)

    def containers(self):
            return self._configuration_from_fixture

    def networks(self):
    networks = []
            for configuration in self._configuration_from_fixture:
            try:
            if configuration['network'] not in networks:
            networks.append(configuration['network'])
    except KeyError:
    pass
    return sorted(networks)

    def volumes(self):
    volumes = []
            for configuration in self._configuration_from_fixture:
            try:
            for image_volume in configuration['volumes']:
            if image_volume not in volumes:
            volumes.append(image_volume)
    except KeyError:
    pass
    return sorted(volumes)

    def container_kwargs(self, container):
    container_dict = {}

    self._container_kwarg(container_dict, container, 'name')
            self._container_kwarg(container_dict, container, 'ports')
            self._container_kwarg(container_dict, container, 'network')
            self._container_kwarg(container_dict, container, 'volumes')
            self._container_kwarg(container_dict, container, 'command')
            self._container_kwarg(container_dict, container, 'environment')

            return container_dict

    def _container_kwarg(self, container_dict, container, key):
            try:
    container_dict[key] = container[key]
    except KeyError:
            logging.warning('%s - missing: %s' % (container['image'], key))
    pass
    return container_dict
    */

    public class ConfigException extends Exception {
        public ConfigException(String message) {
            super(message);
        }
    }
}
