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
    private final HttpConnection httpConnection = new HttpConnection();
    private final ResponseMapper responseMapper = new ResponseMapper();

    public ArrayList<String> lsImages() throws IOException {
        ArrayList<String> imageNames = new ArrayList<>();

        try {
            String json = httpConnection.get("http://127.0.0.1/v1.39/images/json");
            ArrayList<Image> dockerImages = responseMapper.mapJsonIntoImageList(json);

            for (Image dockerImage : dockerImages)
                imageNames.addAll(dockerImage.getRepoTags());
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return imageNames;
    }

    private ArrayList<Map<String, String>> lsContainers(ConfigurationAccessor configurationAccessor)
            throws IOException, IllegalStateException {
        Preconditions.checkNotNull(configurationAccessor);

        ArrayList<Map<String, String>> containersThatMatchConfiguration = new ArrayList<>();

        try {
            String json = httpConnection.get(
                    "http://127.0.0.1/v1.39/containers/json?limit=-1&all=0&size=0&trunc_cmd=0");
            ArrayList<Container> dockerContainers = responseMapper.mapJsonIntoContainerList(json);

            for (Container dockerContainer : dockerContainers)
                for (String containerName : dockerContainer.getNames())
                    for (String image : configurationAccessor.images())
                        if (configurationAccessor.images().contains(containerName)) {
                            Map<String, String> container = new HashMap<>();
                            container.put("image", dockerContainer.getImage());
                            container.put("id", dockerContainer.getId());
                            containersThatMatchConfiguration.add(container);
                        }
        } catch (JsonSyntaxException jsonSyntaxException) {
            logger.warn(jsonSyntaxException.getMessage());
        }

        return containersThatMatchConfiguration;
    }

    public void rmImages(ArrayList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        ArrayList<String> dockerImages = lsImages();
        for (String configurationImage : configurationImages)
            rmImage(configurationImage, dockerImages);
    }

    private void rmImage(String configurationImage, ArrayList<String> dockerImages) throws IOException {
        Preconditions.checkNotNull(configurationImage);
        Preconditions.checkNotNull(dockerImages);

        if (dockerImages.contains(configurationImage)) {
            logger.info(configurationImage);
            httpConnection.delete(String.format(
                    "http://127.0.0.1/v1.39/images/%s?force=True&noprune=False", configurationImage));
        }
    }

    public void pull(ArrayList<String> configurationImages) throws IOException {
        Preconditions.checkNotNull(configurationImages);

        ArrayList<String> dockerImages = lsImages();
        for (String configurationImage : configurationImages) {
            if (!dockerImages.contains(configurationImage)) {
                logger.debug(configurationImage);
                httpConnection.post(
                        String.format("http://127.0.0.1/v1.39/images/create?fromImage=%s", configurationImage));
            }
        }
    }

    public void startContainers(ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        rmContainers(configurationAccessor);
        /*
POST /v1.35/networks/create
< {"Name": "docker_py_wrapper"}
< {"Id":"f0319c779d0b5b01532d093abfd54010b2a671e210d3070286d38cc2b7450ebe","Warning":""}.


POST /v1.35/containers/create?name=alpine-01
> {"ExposedPorts": {"1234/tcp": {}}, "Tty": false, "OpenStdin": false, "StdinOnce": false, "AttachStdin": false,
 "AttachStdout": false, "AttachStderr": false, "Cmd": ["sleep", "12345"], "Image": "alpine:latest",
 "Volumes": {"/tmp": {}}, "NetworkDisabled": false, "HostConfig": {"NetworkMode": "default",
 "Binds": ["alpine-01:/tmp:rw"], "PortBindings": {"1234/tcp": [{"HostIp": "", "HostPort": "1234"}]}}}
< {"Id":"e0f5f5110f92b661839470adfadf55755caedee0c84fd3119d2e6a2dfc7a1fe8","Warnings":null}.

POST /v1.35/containers/2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5/start

--

POST /v1.35/containers/create?name=busybox-01
> {"Tty": false, "OpenStdin": false, "StdinOnce":false, "AttachStdin": false, "AttachStdout": false,
"AttachStderr": false, "Image": "busybox:latest", "NetworkDisabled": false,
"HostConfig": {"NetworkMode": "docker_py_wrapper"}, "NetworkingConfig": {"docker_py_wrapper": null}}
< {"Id":"2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5","Warnings":null}

POST /v1.35/containers/2976e872fae3cd6614a81926ef6c67b95d2cdda02179661694fc55cc252ee9f5/start

*/

        // rm old containers first!
    }

    private void startContainer(ConfigurationAccessor configurationAccessor, String container) {
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

    public void rmContainers(ConfigurationAccessor configurationAccessor) throws IOException {
        Preconditions.checkNotNull(configurationAccessor);

        ArrayList<Map<String, String>> dockerContainers = lsContainers(configurationAccessor);
        ArrayList<String> dockerImages = lsImages();

        for (Map<String, String> dockerCointainer: dockerContainers) {
            if (dockerImages.contains(dockerCointainer.get("image"))) {
                logger.debug(String.format("%s - %s"), dockerCointainer.get("image"), dockerCointainer.get("id"));
                httpConnection.delete(
                        String.format("http://127.0.0.1/v1.39/containers/%s?v=False&link=False&force=True",
                                dockerCointainer.get("id")));
            }
        }

        httpConnection.post("http://127.0.0.1/v1.39/containers/prune");
        httpConnection.post("http://127.0.0.1/v1.39/networks/prune");
        httpConnection.post("http://127.0.0.1/v1.39/volumes/prune");
    }

    public ArrayList lsNetworks(ConfigurationAccessor configurationAccessor) {
        /*
        GET /v1.35/networks?filters=%7B%7D


        networks = []
        for network in self._client.networks.list():
            for config_network in config_networks:
                if config_network == network.name:
                    logging.debug(network.name)
                    networks.append(network.name)
        return networks
         */
        return new ArrayList<String>();
    }

    private void startNetwork() {
        /*
        GET /v1.35/networks/f0319c779d0b5b01532d093abfd54010b2a671e210d3070286d38cc2b7450ebe
        < {"Name":"docker_py_wrapper","Id":"f0319c779d0b5b01532d093abfd54010b2a671e210d3070286d38cc2b7450ebe",
        "Created":"2018-12-07T14:38:22.73110316Z","Scope":"local","Driver":"bridge","EnableIPv6":false,
        "IPAM":{"Driver":"default","Options":null,"Config":[{"Subnet":"172.25.0.0/16","Gateway":"172.25.0.1"}]},
        "Internal":false,"Attachable":false,"Ingress":false,
        "ConfigFrom":{"Network":""},"ConfigOnly":false,"Containers":{},"Options":{},"Labels":{}}.


        if network_to_start not in self.ls_networks(config.networks()):
            logging.debug('%s', network_to_start)
            self._client.networks.create(network_to_start)
         */
    }

    public ArrayList lsVolumes(ConfigurationAccessor configurationAccessor) {
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
        return new ArrayList<String>();
    }
}
