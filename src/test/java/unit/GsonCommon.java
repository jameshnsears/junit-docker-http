package unit;

import com.google.gson.Gson;

import java.io.InputStreamReader;

public class GsonCommon {
    protected final Gson gson;

    protected GsonCommon() {
        gson = new Gson();
    }

    protected InputStreamReader getInputStreamReader(final String s) {
        return new InputStreamReader(getClass().getResourceAsStream(s));
    }
}