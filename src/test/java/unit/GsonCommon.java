package unit;

import java.io.InputStreamReader;

import com.google.gson.Gson;

public class GsonCommon {
    protected final Gson gson;

    protected GsonCommon() {
        gson = new Gson();
    }

    protected InputStreamReader getInputStreamReader(final String pathToFile) {
        return new InputStreamReader(getClass().getResourceAsStream(pathToFile));
    }
}