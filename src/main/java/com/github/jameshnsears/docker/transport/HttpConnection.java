package com.github.jameshnsears.docker.transport;

import com.github.jameshnsears.docker.DockerClient;
import com.google.common.base.Preconditions;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);

    private OkHttpClient okHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(new File("/var/run/docker.sock")))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public String get(final String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        final Request request = new Request.Builder()
                .url(endpoint)
                .build();

        final Response response = okHttpClient().newCall(request).execute();
        final String jsonResponse = response.body().string();
        logger.debug(jsonResponse.replace("\n", ""));
        logger.info(String.format("%s", response.code()));

        return jsonResponse;
    }

    public void post(final String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        final Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(null, new byte[0]))
                .header("Content-Length", "0")
                .build();

        executePost(request);
    }

    public Response post(final String endpoint, final String json) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);
        logger.debug(json);

        final Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .header("Content-Length", "0")
                .build();

        return executePost(request);
    }

    private Response executePost(final Request request) throws IOException {
        Preconditions.checkNotNull(request);

        final Response response = okHttpClient().newCall(request).execute();

        logger.info(String.format("%s", response.code()));
        return response;
    }

    public void delete(final String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        final Request request = new Request.Builder()
                .url(endpoint)
                .delete()
                .build();

        final Response response = okHttpClient().newCall(request).execute();
        logger.info(String.format("%s", response.code()));
    }
}
