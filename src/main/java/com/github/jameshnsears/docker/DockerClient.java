package com.github.jameshnsears.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.github.jameshnsears.docker.transport.HttpConnection;
import com.github.jameshnsears.docker.utils.ResponseMapper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DockerClient {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    HttpConnection httpConnection = new HttpConnection();
    ResponseMapper responseMapper = new ResponseMapper();

    public ArrayList<String> lsImages() throws IOException {
        ArrayList<String> imageNames = new ArrayList<>();

        try {
            String json = httpConnection.get("http://127.0.0.1/v1.39/images/json");
            ArrayList<Image> dockerImages = responseMapper.mapJsonIntoImageList(json);

            for (Image dockerImage : dockerImages)
                for (String repoTag : dockerImage.getRepoTags())
                    imageNames.add(repoTag);
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return imageNames;
    }

    public ArrayList<Map<String, Object>> lsContainers(ConfigurationAccessor configurationAccessor) throws IOException, IllegalStateException {
        Preconditions.checkNotNull(configurationAccessor);

        ArrayList<Map<String, Object>> containersThatMatchConfiguration = new ArrayList<>();

        try {
            String json = httpConnection.get("http://127.0.0.1/v1.39/containers/json");
            ArrayList<Container> dockerContainers = responseMapper.mapJsonIntoContainerList(json);

            for (Container dockerContainer : dockerContainers)
                for (String containerName : dockerContainer.getNames())
                    for (String image : configurationAccessor.images())
                        if (configurationAccessor.images().contains(containerName)) {
                            Map<String, Object> container = new HashMap<>();
                            container.put("image", dockerContainer.getImage());
                            container.put("id", dockerContainer.getId());
                            container.put("container", dockerContainer);
                            containersThatMatchConfiguration.add(container);
                        }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return containersThatMatchConfiguration;
    }

    public void rmImages(ArrayList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages );

        ArrayList<String> dockerImages = lsImages();
        for (String configurationImage : configurationImages)
            rmImage(configurationImage, dockerImages);
    }

    private void rmImage(String configurationImage, ArrayList<String> dockerImages) throws IOException {
        Preconditions.checkNotNull(configurationImage);
        Preconditions.checkNotNull(dockerImages);

        logger.info(configurationImage);

        if (dockerImages.contains(configurationImage)) {
            logger.debug(configurationImage);
            httpConnection.delete(String.format(
                    "http://127.0.0.1/v1.39/images/%s?force=True&noprune=False", configurationImage));
        }
    }

    public void pull(ArrayList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        ArrayList<String> dockerImages = lsImages();
        for (String configurationImage : configurationImages)
          if (dockerImages.contains(configurationImage)) {
              logger.debug(configurationImage);
              //         POST /v1.39/images/create?tag=latest&fromImage=alpine
              httpConnection.post(String.format(
                      "http://127.0.0.1/v1.39/images/create?fromImage=%s", configurationImage));
          }

        // some sort of callback?

        /*
        for image_to_pull in images_to_pull:
            if image_to_pull not in self.ls_images():
                logging.debug(image_to_pull)
                try:
                    self._client.images.pull(image_to_pull)
                except com.github.jameshnsears.docker.errors.ImageNotFound as exception:
                    logging.error(str(exception))
                    return False
            else:
                logging.debug('skipped: %s', image_to_pull)
        return True
         */

        /*
        GET /v1.39/images/alpine:latest/json

        POST /v1.39/containers/create?name=alpine-01

        DELETE /v1.39/containers/75fd619ebb6623448df989816e337fb80910e4a7e9aa5db496662e96a0b217b6?v=False&link=False&force=True

        POST /v1.39/containers/prune

        POST /v1.39/networks/prune

        GET /v1.39/networks?filters=%7B%7D

        GET /v1.39/volumes

        POST /v1.39/volumes/prune
         */
    }

    public void startContainers() {
        /*
        self.rm_containers(config.images())
        for container in config.containers():
            self._start_container(config, container)
         */
    }

    private void startContainer() {
        /*
        try:
            logging.debug(container['image'])

            try:
                self._start_network(config, container['network'])
            except KeyError:
                pass

            detached_container = self._client.containers.run(container['image'],
                                                             ** config.container_kwargs(container),
                                                             detach=True)
            logging.debug(detached_container.attrs['Id'])
        except KeyError:
            logging.error('missing: image')
            pass
         */
    }

    public void rmContainers() {
        /*
        for docker_container in self.ls_containers(containers_to_stop):
            for container_to_stop in containers_to_stop:
                if docker_container['image'] == container_to_stop:
                    self._rm_container_artefacts(docker_container)

        self._client.containers.prune()
        self._client.networks.prune()
         */
    }

    private void rmContainerArtefacts() {
        /*
        logging.debug(docker_container['id'])
        try:
            docker_container['container'].stop(timeout=1)
            docker_container['container'].remove(force=True)
        except com.github.jameshnsears.docker.errors.NotFound:
            pass
        self._client.volumes.prune()
         */
    }

    public void lsNetworks() {
        /*
        networks = []
        for network in self._client.networks.list():
            for config_network in config_networks:
                if config_network == network.name:
                    logging.debug(network.name)
                    networks.append(network.name)
        return networks
         */
    }

    private void startNetwork() {
        /*
        if network_to_start not in self.ls_networks(config.networks()):
            logging.debug('%s', network_to_start)
            self._client.networks.create(network_to_start)
         */
    }

    public void lsVolumes() {
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
        return;
    }

    private void stopContainer() {
        return;
    }
}
