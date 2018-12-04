package com.github.jameshnsears.docker.transport;

import com.github.jameshnsears.docker.DockerClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.unixdomainsockets.UnixDomainSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);
    private OkHttpClient okHttpClient;

    public HttpConnection() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        okHttpClient = new OkHttpClient.Builder()
                .socketFactory(new UnixDomainSocketFactory(new File("/var/run/docker.sock")))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public String get(String endpoint) throws IOException {
        /*
        HttpUrl url = new HttpUrl.Builder()
            .host(host).addQueryParameter(name, value).build();

Request request = new Request.Builder()
            .url(url).post(RequestBody.create(mediaType, body)).addHeader(type, header).build();

okhttpClient.newCall(request).enqueue(new Callback() {
    ...
});
         */
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

//        switch (httpVerb) {
//            case GET:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .build();
//                break;
//
//            case POST:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .post(RequestBody.create())
//                        .build();
//                break;
//
//            case DELETE:
//                request = new Request.Builder()
//                        .url(endpoint)
//                        .build();
//                break;
//        }

        String jsonResponse = okHttpClient.newCall(request).execute().body().string();
        logger.debug(String.format("jsonResponse=%s", jsonResponse));
        return jsonResponse;
    }

    public String post(String endpoint) throws IOException {
        return "";
    }

    public String delete(String endpoint) throws IOException {
        return "";
    }

    private enum HttpVerb {
        GET, POST, DELETE
    }

}
