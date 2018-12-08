package com.github.jameshnsears.docker;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.models.Network;
import com.github.jameshnsears.docker.transport.HttpConnection;
import com.github.jameshnsears.docker.utils.ModelMapper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DockerClient {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private final HttpConnection httpConnection = new HttpConnection();
    private final ModelMapper modelMapper = new ModelMapper();

    public AbstractList<String> lsImages() throws IOException {
        final ArrayList<String> imageNames = new ArrayList<>();

        try {
            final String json = httpConnection.get("http://127.0.0.1/v1.39/images/json");
            final ArrayList<Image> dockerImages = (ArrayList) modelMapper.mapJsonIntoImages(json);

            for (final Image dockerImage : dockerImages) {
                imageNames.addAll(dockerImage.getRepoTags());
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return imageNames;
    }

    private AbstractList<Map<String, String>> lsContainers(final ConfigurationAccessor configurationAccessor)
            throws IOException, IllegalStateException {
        Preconditions.checkNotNull(configurationAccessor);

        final ArrayList<Map<String, String>> dockerContainersThatMatchConfiguration = new ArrayList<>();

        try {
            final String json = httpConnection.get(
                    "http://127.0.0.1/v1.39/containers/json?limit=-1&all=0&size=0&trunc_cmd=0");
            final ArrayList<Container> dockerContainers = (ArrayList) modelMapper.mapJsonIntoContainers(json);

            for (final Container dockerContainer : dockerContainers) {
                for (final String containerName : dockerContainer.getNames()) {
                    if (configurationAccessor.images().contains(containerName)) {
                        final Map<String, String> container = new ConcurrentHashMap<>();
                        container.put("image", dockerContainer.getImage());
                        container.put("id", dockerContainer.getId());
                        dockerContainersThatMatchConfiguration.add(container);
                    }
                }
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return dockerContainersThatMatchConfiguration;
    }

    public void rmImages(final AbstractList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        final ArrayList<String> dockerImages = (ArrayList<String>) lsImages();
        for (final String configurationImage : configurationImages) {
            rmImage(configurationImage, dockerImages);
        }
    }

    private void rmImage(final String configurationImage, final AbstractList<String> dockerImages) throws IOException {
        Preconditions.checkNotNull(configurationImage);
        Preconditions.checkNotNull(dockerImages);

        if (dockerImages.contains(configurationImage)) {
            logger.info(configurationImage);
            httpConnection.delete(String.format(
                    "http://127.0.0.1/v1.39/images/%s?force=True&noprune=False", configurationImage));
        }
    }

    public void pull(final AbstractList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        final ArrayList<String> dockerImages = (ArrayList) lsImages();
        for (final String configurationImage : configurationImages) {
            if (!dockerImages.contains(configurationImage)) {
                logger.debug(configurationImage);
                httpConnection.post(
                        String.format("http://127.0.0.1/v1.39/images/create?fromImage=%s", configurationImage));
            }
        }
    }

    public void startContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        rmContainers(configurationAccessor);
        createNetworks(configurationAccessor);

        // create container
        final Collection<Configuration> configurationContainers = configurationAccessor.containers();
        for (final Configuration configurationContainer : configurationContainers) {
            httpConnection.post(
                    String.format("http://127.0.0.1/v1.39/containers/create?name=%s", configurationContainer.getName()),
                    jsonForContainerCreation(configurationContainer));

//            configurationContainer.getImage();  // alpine:latest
//            configurationContainer.getCommand(); // to split into white space seperated values

            // TODO start container
        }
    }

    private String jsonForContainerCreation(final Configuration configurationContainer) {
        String json = null;
        /*
POST /v1.35/containers/create?name=alpine-01
{
"ExposedPorts": {"1234/tcp": {}},
"Tty": false,
"OpenStdin": false,
"StdinOnce": false,
"AttachStdin": false,
"AttachStdout": false,
"AttachStderr": false,
"Cmd": ["sleep", "12345"],
"Image": "alpine:latest",
"Volumes": {"/tmp": {}},
"NetworkDisabled": false,
"HostConfig": {
    "NetworkMode": "default",
    "Binds": ["alpine-01:/tmp:rw"],
    "PortBindings": {"1234/tcp": [{"HostIp": "", "HostPort": "1234"}]}}}

< {"Id":"e0f5f5110f92b661839470adfadf55755caedee0c84fd3119d2e6a2dfc7a1fe8","Warnings":null}.

POST /v1.35/containers/2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5/start

--

POST /v1.35/containers/create?name=busybox-01
>
{"Tty": false,
"OpenStdin": false,
"StdinOnce":false,
"AttachStdin": false,
"AttachStdout": false,
"AttachStderr": false,
"Image": "busybox:latest",
"NetworkDisabled": false,
"HostConfig": {"NetworkMode": "docker_py_wrapper"},
"NetworkingConfig": {"docker_py_wrapper": null}}

< {"Id":"2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5","Warnings":null}

POST /v1.35/containers/2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5/start

*/
        return json;
    }

    public void rmContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        final ArrayList<Map<String, String>> dockerContainers = (ArrayList) lsContainers(configurationAccessor);
        final ArrayList<String> dockerImages = (ArrayList) lsImages();

        for (final Map<String, String> dockerCointainer : dockerContainers) {
            if (dockerImages.contains(dockerCointainer.get("image"))) {
                logger.debug(String.format("%s - %s", dockerCointainer.get("image"), dockerCointainer.get("id")));
                httpConnection.delete(
                        String.format("http://127.0.0.1/v1.39/containers/%s?v=False&link=False&force=True",
                                dockerCointainer.get("id")));
            }
        }

        pruneEnvironment();
    }

    private void pruneEnvironment() throws IOException {
        httpConnection.post("http://127.0.0.1/v1.39/containers/prune");
        httpConnection.post("http://127.0.0.1/v1.39/networks/prune");
        httpConnection.post("http://127.0.0.1/v1.39/volumes/prune");
    }

    private void createNetworks(final ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        final ArrayList<String> configurationNetworks = (ArrayList) configurationAccessor.networks();
        final ArrayList<String> dockerNetworks = (ArrayList) lsNetworks();
        for (final String configurationNetwork : configurationNetworks) {
            if (!dockerNetworks.contains(configurationNetwork)) {
                createNetwork(configurationAccessor, configurationNetwork);
            }
        }
    }

    private void createNetwork(final ConfigurationAccessor configurationAccessor,
                               final String networkToCreate) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);
        Preconditions.checkNotNull(networkToCreate);

        logger.info(networkToCreate);
        httpConnection.post(
                "http://127.0.0.1/v1.39/networks/create",
                String.format("{\"Name\": \"%s\"}", networkToCreate));
    }

    private AbstractList<String> lsNetworks() throws IOException {
        final String json = httpConnection.get(
                "http://127.0.0.1/v1.39/networks?filters=%7B%7D");
        final ArrayList<Network> dockerNetworks = (ArrayList) modelMapper.mapJsonIntoNetworks(json);

        final ArrayList<String> networks = new ArrayList<>();
        for (final Network dockerNetwork : dockerNetworks) {
            networks.add(dockerNetwork.getName());
        }

        return networks;
    }

    public AbstractList<String> lsVolumes(final ConfigurationAccessor configurationAccessor) {
        /*
        self._client.volumes.prune()
        volumes = []
        for volume in self._client.volumes.list():
            for config_volume in config_volumes:
                if volume.name in config_volume:
                    logging.debug(config_volume)
                    volumes.append(config_volume)
        return sorted(volumes)
         */
        return new ArrayList<>();
    }
}
