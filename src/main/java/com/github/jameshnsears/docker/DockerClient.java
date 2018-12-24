package com.github.jameshnsears.docker;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jameshnsears.configuration.Configuration;
import com.github.jameshnsears.configuration.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.ContainerResponse;
import com.github.jameshnsears.docker.models.ImageResponse;
import com.github.jameshnsears.docker.models.NetworkResponse;
import com.github.jameshnsears.docker.transport.HttpConnection;
import com.github.jameshnsears.docker.utils.RequestMapper;
import com.github.jameshnsears.docker.utils.ResponseMapper;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.Response;


public class DockerClient {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private final HttpConnection httpConnection = new HttpConnection();
    private final ResponseMapper responseMapper = new ResponseMapper();
    private final RequestMapper containerCreateMapper = new RequestMapper();

    public ArrayList<Map<String, String>> lsImages() throws IOException {
        final ArrayList<Map<String, String>> images = new ArrayList<>();

        try {
            final String json = httpConnection.get("http://127.0.0.1/v1.39/images/json");
            final ArrayList<ImageResponse> dockerImages = responseMapper.imagesResponse(json);

            for (final ImageResponse dockerImage : dockerImages) {
                final Map<String, String> dockerImageMap = new HashMap<>();
                dockerImageMap.put("id", dockerImage.id);
                dockerImageMap.put("name", dockerImage.repoTags.get(0));
                images.add(dockerImageMap);
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return images;
    }

    public ArrayList<Map<String, String>> lsContainers(final ConfigurationAccessor configurationFilter)
            throws IOException, IllegalStateException {
        Preconditions.checkNotNull(configurationFilter);

        final ArrayList<Map<String, String>> dockerContainersThatMatchConfiguration = new ArrayList<>();

        try {
            final String json = httpConnection.get(
                    "http://127.0.0.1/v1.39/containers/json");
            final ArrayList<ContainerResponse> dockerContainers = responseMapper.containersResponse(json);

            for (final ContainerResponse dockerContainer : dockerContainers) {
                for (String containerName : dockerContainer.names) {
                    containerName = containerName.replaceFirst("/", "");

                    if (configurationFilter.imageNames().contains(containerName)) {
                        final Map<String, String> container = new ConcurrentHashMap<>();
                        container.put("imageId", dockerContainer.imageId);
                        container.put("id", dockerContainer.id);
                        container.put("name", containerName);
                        dockerContainersThatMatchConfiguration.add(container);
                    }
                }
            }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return dockerContainersThatMatchConfiguration;
    }

    public void rmImages(final AbstractList<String> configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final ArrayList<Map<String, String>> dockerImages = lsImages();
        for (final String configurationImage : configurationFilter) {
            rmImage(configurationImage, dockerImages);
        }
    }

    private void rmImage(final String configurationImage,
                         final ArrayList<Map<String, String>> dockerImages) throws IOException {
        Preconditions.checkNotNull(configurationImage);
        Preconditions.checkNotNull(dockerImages);

        for (final Map<String, String> dockerImage : dockerImages) {
            if (dockerImage.get("name").equals(configurationImage)) {
                logger.info(configurationImage);
                httpConnection.delete(String.format(
                        "http://127.0.0.1/v1.39/images/%s?force=True&noprune=False", configurationImage));
            }
        }
    }

    public void pull(final AbstractList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        final ArrayList<Map<String, String>> dockerImages = lsImages();
        for (final String configurationImageName : configurationImages) {
            if (shouldImageBePulled(dockerImages, configurationImageName)) {
                logger.info(configurationImageName);
                final Response response = httpConnection.post(
                        String.format("http://127.0.0.1/v1.39/images/create?fromImage=%s", configurationImageName));

                logger.debug(response.body().string());
            }
        }
    }

    private boolean shouldImageBePulled(final ArrayList<Map<String, String>> dockerImages,
                                        final String configurationImageName) {
        Preconditions.checkNotNull(dockerImages);
        Preconditions.checkNotNull(configurationImageName);

        final List<String> imageNamesAlreadyPulled = new ArrayList<>();
        for (final Map<String, String> dockerImage : dockerImages) {
            imageNamesAlreadyPulled.add(dockerImage.get("name"));
        }

        return !imageNamesAlreadyPulled.contains(configurationImageName);
    }

    public void startContainers(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        rmContainers(configurationFilter);
        createNetworks(configurationFilter);
        createVolumes(configurationFilter);

        final Collection<Configuration> configurationContainers = configurationFilter.containers();

        for (final Configuration configurationContainer : configurationContainers) {
            logger.info(configurationContainer.name);
            final Response response = httpConnection.post(
                    String.format("http://127.0.0.1/v1.39/containers/create?name=%s", configurationContainer.name),
                    containerCreateMapper.containerCreateRequest(configurationContainer));

            httpConnection.post(
                    String.format("http://127.0.0.1/v1.39/containers/%s/start",
                            responseMapper.containerCreateResponse(response).id));
        }
    }

    public void rmContainers(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final ArrayList<Map<String, String>> dockerImages = lsImages();
        final ArrayList<Map<String, String>> dockerContainers = lsContainers(configurationFilter);

        for (final Map<String, String> dockerCointainer : dockerContainers) {
            for (final Map<String, String> dockerImage : dockerImages) {
                if (dockerCointainer.get("imageId").equals(dockerImage.get("id"))) {
                    logger.debug(String.format("%s - %s", dockerCointainer.get("name"), dockerCointainer.get("id")));
                    httpConnection.delete(
                            String.format("http://127.0.0.1/v1.39/containers/%s?v=False&link=False&force=True",
                                    dockerCointainer.get("id")));
                }
            }
        }

        pruneContainers();
        pruneNetworks();
        pruneVolumes();
    }

    private void pruneContainers() throws IOException {
        httpConnection.post("http://127.0.0.1/v1.39/containers/prune");
    }

    private void pruneNetworks() throws IOException {
        httpConnection.post("http://127.0.0.1/v1.39/networks/prune");
    }

    private void pruneVolumes() throws IOException {
        httpConnection.post("http://127.0.0.1/v1.39/volumes/prune");
    }

    private void createNetworks(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final ArrayList<String> configurationNetworks = configurationFilter.networks();

        for (final String configurationNetwork : configurationNetworks) {
            final ArrayList<String> dockerNetworks = lsNetworks(configurationFilter);
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

    private void createVolumes(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final ArrayList<String> configurationVolumes = configurationFilter.volumes();

        if (!configurationVolumes.isEmpty()) {
            for (final String configurationVolume : configurationVolumes) {
                final ArrayList<String> dockerVolumes = lsVolumes(configurationFilter);
                if (!dockerVolumes.contains(configurationVolume)) {
                    createVolume(configurationVolume);
                }
            }
        }
    }

    private void createVolume(final String volumeToCreate) throws IOException {
        Preconditions.checkNotNull(volumeToCreate);

        logger.info(volumeToCreate);
        httpConnection.post(
                "http://127.0.0.1/v1.39/volumes/create",
                String.format(
                        "{\"Driver\":\"local\",\"DriverOpts\":{},\"Labels\":{},\"Name\":\"%s\"}",
                        volumeToCreate));
    }

    public ArrayList<String> lsNetworks(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final String json = httpConnection.get("http://127.0.0.1/v1.39/networks");
        logger.debug(prettyPrintJson(json));

        final ArrayList<NetworkResponse> dockerNetworks = responseMapper.networksResponse(json);

        final ArrayList<String> configurationNetworks = configurationFilter.networks();

        final ArrayList<String> networks = new ArrayList<>();
        for (final NetworkResponse dockerNetwork : dockerNetworks) {
            if (configurationNetworks.contains(dockerNetwork.name)) {
                logger.debug(dockerNetwork.name);
                networks.add(dockerNetwork.name);
            }
        }

        return networks;
    }

    public ArrayList<String> lsVolumes(final ConfigurationAccessor configurationFilter) throws IOException {
        Preconditions.checkNotNull(configurationFilter);

        final String json = httpConnection.get("http://127.0.0.1/v1.39/volumes");
        logger.debug(prettyPrintJson(json));

        final ArrayList<String> volumes = new ArrayList<>();

        if ("{}".equals(json)) {
            logger.debug("json empty");
            return volumes;
        }

        final Map<String, List<Map<String, Object>>> dockerVolumes = responseMapper.volumeResponse(json);

        final ArrayList<String> configurationVolumes = configurationFilter.volumes();

        for (final Map<String, Object> dockerVolume : dockerVolumes.get("Volumes")) {
            final String dockerVolumerName = (String) dockerVolume.get("Name");
            if (configurationVolumes.contains(dockerVolumerName)) {
                logger.debug(dockerVolumerName);
                volumes.add(dockerVolumerName);
            }
        }

        return volumes;
    }

    private String prettyPrintJson(final String jsonString) {
        final JsonElement jsonElement = new JsonParser().parse(jsonString);
        final Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        return gsonPrettyPrinter.toJson(jsonElement);
    }
}
