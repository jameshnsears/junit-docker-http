package com.github.jameshnsears;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class DockerHttp {
    private static final Logger log = LoggerFactory.getLogger(DockerHttp.class);

    public static void main(String... args) throws Exception {
        DockerHttp dockerHttp = new DockerHttp();
        dockerHttp.lsContainers();
    }

    public void lsContainers() throws Exception {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.HEADERS);

        File socketFile = new File("/var/run/docker.sock");

        OkHttpClient client = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(socketFile))
                .addInterceptor(logging)
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1/v1.39/images/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            String jsonInString = response.body().string();

            // ignore any parts of the json we're not interested in!
            User user gson.fromJson(jsonInString, User.class);
            log.info(response.body().string());
        }
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

    public void ls_containers() {
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
