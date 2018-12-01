package com.github.jameshnsears;

import okhttp3.Interceptor;
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
        new DockerHttp().run();
    }

    public void run() throws Exception {
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
            log.info(response.body().string());
        }
    }
}
