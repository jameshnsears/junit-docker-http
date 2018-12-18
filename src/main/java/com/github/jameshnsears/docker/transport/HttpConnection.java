package com.github.jameshnsears.docker.transport;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jameshnsears.docker.DockerClient;
import com.google.common.base.Preconditions;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;

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

        // TODO pretty print?.
        logger.debug(jsonResponse.replace("\n", ""));

        logger.info(String.format("%s", response.code()));

        return jsonResponse;
    }

    public Response post(final String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.info(endpoint);

        final Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(null, new byte[0]))
                .header("Content-Length", "0")
                .build();

        return getResponse(request);
    }

    private Response getResponse(Request request) throws IOException {
        final Response response = okHttpClient().newCall(request).execute();

        logger.info(String.format("%s", response.code()));
        return response;
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

        return getResponse(request);
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
