package com.github.jameshnsears.docker;

import com.github.jameshnsears.Configuration;
import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.ContainerResponse;
import com.github.jameshnsears.docker.models.ImageResponse;
import com.github.jameshnsears.docker.models.NetworkResponse;
import com.github.jameshnsears.docker.transport.HttpConnection;
import com.github.jameshnsears.docker.utils.RequestMapper;
import com.github.jameshnsears.docker.utils.ResponseMapper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonSyntaxException;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DockerClient {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private final HttpConnection httpConnection = new HttpConnection();
    private final ResponseMapper responseMapper = new ResponseMapper();
    private final RequestMapper containerCreateMapper = new RequestMapper();

    public ArrayList<String> lsImages() throws IOException {
        final ArrayList<String> imageNames = new ArrayList<>();

        try {
            final String json = httpConnection.get("http://127.0.0.1/v1.39/images/containerCreateRequest");
            final ArrayList<ImageResponse> dockerImages = responseMapper.imagesResponse(json);

            for (final ImageResponse dockerImage : dockerImages) {
                imageNames.addAll(dockerImage.repoTags);
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return imageNames;
    }

    public ArrayList<Map<String, String>> lsContainers(final ConfigurationAccessor configurationAccessor)
            throws IOException, IllegalStateException {
        Preconditions.checkNotNull(configurationAccessor);

        final ArrayList<Map<String, String>> dockerContainersThatMatchConfiguration = new ArrayList<>();

        try {
            final String json = httpConnection.get(
                    "http://127.0.0.1/v1.39/containers/json?limit=-1&all=0&size=0&trunc_cmd=0");
            final ArrayList<ContainerResponse> dockerContainers = responseMapper.containersResponse(json);

            for (final ContainerResponse dockerContainer : dockerContainers) {
                for (String containerName : dockerContainer.names) {
                    containerName = containerName.replaceFirst("/", "");

                    if (configurationAccessor.imageNames().contains(containerName)) {
                        Map<String, String> container = new ConcurrentHashMap<>();
                        container.put("name", containerName);
                        container.put("id", dockerContainer.id);
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

        final ArrayList<String> dockerImages = lsImages();
        for (final String configurationImage : configurationImages) {
            if (!dockerImages.contains(configurationImage)) {
                logger.info(configurationImage);
                httpConnection.post(
                        String.format("http://127.0.0.1/v1.39/images/create?fromImage=%s", configurationImage));
            }
        }
    }

    public void startContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        rmContainers(configurationAccessor);
        createNetworks(configurationAccessor);

        final Collection<Configuration> configurationContainers = configurationAccessor.containers();

        for (final Configuration configurationContainer : configurationContainers) {
            logger.info(configurationContainer.name);
            Response response = httpConnection.post(
                    String.format("http://127.0.0.1/v1.39/containers/create?name=%s", configurationContainer.name),
                    containerCreateMapper.containerCreateRequest(configurationContainer));

            httpConnection.post(
                    String.format("http://127.0.0.1/v1.39/containers/%s/start",
                            responseMapper.containerCreateResponse(response).id));
        }
    }

    public void rmContainers(final ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        final ArrayList<Map<String, String>> dockerContainers = lsContainers(configurationAccessor);
        final ArrayList<String> dockerImages = lsImages();

        for (final Map<String, String> dockerCointainer : dockerContainers) {
            if (dockerImages.contains(dockerCointainer.get("image"))) {
                logger.debug(String.format("%s - %s", dockerCointainer.get("name"), dockerCointainer.get("id")));
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

        final ArrayList<String> configurationNetworks = configurationAccessor.networks();
        final ArrayList<String> dockerNetworks = lsNetworks();
        for (final String configurationNetwork : configurationNetworks) {
            if (!dockerNetworks.contains(configurationNetwork)) {
                createNetwork(configurationNetwork);
            }
        }
    }

    private void createNetwork(final String networkToCreate) throws IOException {
        Preconditions.checkNotNull(networkToCreate);

        logger.info(networkToCreate);
        httpConnection.post(
                "http://127.0.0.1/v1.39/networks/create",
                String.format("{\"Name\": \"%s\"}", networkToCreate));
    }

    public ArrayList<String> lsNetworks() throws IOException {
        final String json = httpConnection.get(
                "http://127.0.0.1/v1.39/networks?filters=%7B%7D");
        final ArrayList<NetworkResponse> dockerNetworks = responseMapper.networksResponse(json);

        final ArrayList<String> networks = new ArrayList<>();
        for (final NetworkResponse dockerNetwork : dockerNetworks) {
            logger.debug(dockerNetwork.name);
            networks.add(dockerNetwork.name);
        }

        return networks;
    }

    public ArrayList<String> lsVolumes() throws IOException {
        final String json = httpConnection.get(
                "http://127.0.0.1/v1.39/volumes");
        final Map<String, List<Map<String, Object>>> dockerVolumes = responseMapper.volumeResponse(json);

        ArrayList<String> volumes = new ArrayList<>();
        for (Map<String, Object> volume : dockerVolumes.get("Volumes")) {
            volumes.add((String) volume.get("Name"));
        }

        return volumes;
    }
}
