package com.github.jameshnsears.docker.transport;

import com.github.jameshnsears.docker.DockerClient;
import com.google.common.base.Preconditions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);

    private OkHttpClient okHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(new File("/var/run/docker.sock")))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public String get(String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        Response response = okHttpClient().newCall(request).execute();
        String jsonResponse = response.body().string();
        logger.debug(jsonResponse.replace("\n", ""));
        logger.debug(String.format("%s", response.code()));

        return jsonResponse;
    }

    public void post(String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(null, new byte[0]))
                .header("Content-Length", "0")
                .build();

        Response response = okHttpClient().newCall(request).execute();
        logger.debug(response.body().string());
        logger.debug(String.format("%s", response.code()));
    }

    public void delete(String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .delete()
                .build();

        Response response = okHttpClient().newCall(request).execute();
        logger.debug(String.format("%s", response.code()));
    }
}
