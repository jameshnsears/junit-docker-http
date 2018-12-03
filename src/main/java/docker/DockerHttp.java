package docker;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class DockerHttp {
    private static final Logger log = LoggerFactory.getLogger(DockerHttp.class);
    private HttpLoggingInterceptor logging;
    private File socketFile;
    private OkHttpClient client;

    public DockerHttp() {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.HEADERS);

        socketFile = new File("/var/run/docker.sock");

        client = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(socketFile))
                .addInterceptor(logging)
                .build();
    }

    public String lsContainers() throws Exception {
        Request request = new Request.Builder()
                .url("http://127.0.0.1/v1.39/images/json0")  // /v1.39/images/json?only_ids=0
                .build();

        String jsonResponse = client.newCall(request).execute().body().string();
        log.debug(jsonResponse);

        return jsonResponse;
    }

    public void ls_images() {
        return;
    }

    public void rm_images() {
        return;
    }

    private void rm_image() {
        return;
    }

    public void pull() {
        /*
        curl --unix-socket /var/run/docker.sock -X POST "http:/v1.39/images/create?fromImage=alpine"

        POST /v1.35/images/create?tag=latest&fromImage=alpine

        GET /v1.35/images/json?only_ids=0&all=0

        DELETE /v1.35/images/alpine:latest?force=True&noprune=False

        POST /v1.35/images/create?tag=latest&fromImage=alpine

        GET /v1.35/images/alpine:latest/json

        POST /v1.35/containers/create?name=alpine-01

        DELETE /v1.35/containers/75fd619ebb6623448df989816e337fb80910e4a7e9aa5db496662e96a0b217b6?v=False&link=False&force=True

        POST /v1.35/containers/prune

        POST /v1.35/networks/prune

        GET /v1.35/networks?filters=%7B%7D

        GET /v1.35/volumes

        POST /v1.35/volumes/prune
         */
        return;
    }

    public void start_containers() {
        return;
    }

    private void start_container() {
        return;
    }

    public void rm_containers() {
        return;
    }

    private void rm_container_artefacts() {
        return;
    }

    public void ls_networks() {
        return;
    }

    private void start_network() {
        return;
    }

    public void ls_volumes() {
        return;
    }

    /////////////

    private void stop_container() {
        /*
        curl --unix-socket /var/run/docker.sock http:/v1.39/containers/json
[{
  "Id":"ae63e8b89a26f01f6b4b2c9a7817c31a1b6196acf560f66586fbc8809ffcd772",
  "Names":["/tender_wing"],
  "Image":"bfirsh/reticulate-splines",
  ...
}]

         */
    }
}
