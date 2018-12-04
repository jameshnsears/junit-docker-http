package com.github.jameshnsears.docker;

import com.github.jameshnsears.ConfigurationAccessor;
import com.github.jameshnsears.docker.models.Container;
import com.github.jameshnsears.docker.models.Image;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class DockerClient {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private OkHttpClient okHttpClient;
    private Gson gson;

    public DockerClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.HEADERS);

        okHttpClient = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(new File("/var/run/docker.sock")))
                .addInterceptor(httpLoggingInterceptor)
                .build();

        gson = new Gson();
    }

    private String callDockerEngine(String endpoint) throws IOException {
        /*
        HttpUrl url = new HttpUrl.Builder()
            .host(host).addQueryParameter(name, value).build();

Request request = new Request.Builder()
            .url(url).post(RequestBody.create(mediaType, body)).addHeader(type, header).build();

okhttpClient.newCall(request).enqueue(new Callback() {
    ...
});
         */
        Request request = new Request.Builder()
                        .url(endpoint)
                        .build();

//        switch (httpVerb) {
//            case GET:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .build();
//                break;
//
//            case POST:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .post(RequestBody.create())
//                        .build();
//                break;
//
//            case DELETE:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .build();
//                break;
//        }

        String jsonResponse = okHttpClient.newCall(request).execute().body().string();
        logger.debug(String.format("jsonResponse=%s", jsonResponse));
        return jsonResponse;
    }

    public ArrayList<String> lsImages() throws IOException {
        ArrayList<String> imageNames = new ArrayList<>();

        try {
            ArrayList<Image> dockerImages = gson.fromJson(
                    callDockerEngine("http://127.0.0.1/v1.39/images/json"),
                    new TypeToken<Collection<Image>>() {}.getType());

            for (Image dockerImage: dockerImages)
                for (String repoTag: dockerImage.getRepoTags())
                    imageNames.add(repoTag);
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return imageNames;
    }

    public ArrayList<Map<String, Object>> lsContainers(ConfigurationAccessor configurationAccessor) throws IOException, IllegalStateException {
        ArrayList<Map<String, Object>> containersThatMatchConfiguration = new ArrayList<>();

        try {
            ArrayList<Container> dockerContainers = gson.fromJson(
                    callDockerEngine("http://127.0.0.1/v1.39/containers/json"),
                    new TypeToken<Collection<Container>>() {
                    }.getType());

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

    public void rmImages(ArrayList<String> images) {
        for (String image : images)
            rmImage(image);
    }

    private void rmImage(String image) {
        // DELETE /v1.39/images/alpine:latest?force=True&noprune=False
        /*
        if image_to_rm in self.ls_images():
            logging.debug(image_to_rm)
            self._client.images.remove(image_to_rm, force=True)
         */
    }

    public void pull() {
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
        POST /v1.39/images/create?tag=latest&fromImage=alpine

        POST /v1.39/images/create?tag=latest&fromImage=alpine

        GET /v1.39/images/alpine:latest/json

        POST /v1.39/containers/create?name=alpine-01

        DELETE /v1.39/containers/75fd619ebb6623448df989816e337fb80910e4a7e9aa5db496662e96a0b217b6?v=False&link=False&force=True

        POST /v1.39/containers/prune

        GET http:/v1.39/containers/json

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
