package com.github.jameshnsears;

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
                .url("http://127.0.0.1/v1.39/images/json")
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

}
