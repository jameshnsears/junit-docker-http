package unit;

import com.google.gson.Gson;

import java.io.InputStreamReader;

public class GsonCommon {
    protected final Gson gson;

    public GsonCommon() {
        gson = new Gson();
    }

    public InputStreamReader getInputStreamReader(String s) {
        return new InputStreamReader(getClass().getResourceAsStream(s));
    }
}