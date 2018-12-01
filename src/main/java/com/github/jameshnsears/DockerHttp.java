package com.github.jameshnsears;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;

import java.io.File;


public class DockerHttp {
    public static void main(String... args) throws Exception {
        new DockerHttp().run();
    }

    public void run() throws Exception {
        File socketFile = new File("/var/run/docker.sock");

        OkHttpClient client = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(socketFile))
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1/v1.39/images/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

    }
}

/*

 */