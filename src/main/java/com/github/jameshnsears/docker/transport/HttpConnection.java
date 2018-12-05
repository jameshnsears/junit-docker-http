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
import java.util.Map;

public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private OkHttpClient okHttpClient;

    public HttpConnection() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        okHttpClient = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(new File("/var/run/docker.sock")))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public String get(String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.debug(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonResponse = response.body().string();
        logger.debug(String.format("%s: jsonResponse=%s", response.code(), jsonResponse));
        return jsonResponse;
    }

    public String post(String endpoint, Map<String, String> formMap) throws IOException {
        Preconditions.checkNotNull(endpoint);
        Preconditions.checkNotNull(formMap);

        logger.debug(endpoint);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        for ( Map.Entry<String, String> entry : formMap.entrySet() )
            formBodyBuilder.add( entry.getKey(), entry.getValue() );

        RequestBody formBody = formBodyBuilder.build();

        Request request = new Request.Builder()
                .url(endpoint)
                .post(formBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        logger.debug(String.format("%s", response.code()));
        return "";
    }

    public void delete(String endpoint) throws IOException {
        Preconditions.checkNotNull(endpoint);

        logger.debug(endpoint);

        Request request = new Request.Builder()
                .url(endpoint)
                .delete()
                .build();

        Response response = okHttpClient.newCall(request).execute();
        logger.debug(String.format("%s", response.code()));
    }
}
