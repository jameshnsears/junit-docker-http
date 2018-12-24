package com.github.jameshnsears;

import com.google.gson.Gson;

import java.io.InputStreamReader;

public class GsonCommon {
    protected final Gson gson;

    protected GsonCommon() {
        gson = new Gson();
    }

    protected InputStreamReader getInputStreamReader(final String pathToFile) {
        return new InputStreamReader(getClass().getResourceAsStream(pathToFile));
    }
}