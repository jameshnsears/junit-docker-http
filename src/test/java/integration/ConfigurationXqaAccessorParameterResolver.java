package integration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

import com.google.common.io.CharStreams;
import unit.docker.ConfigurationAccessorParameterResolver;

public class ConfigurationXqaAccessorParameterResolver extends ConfigurationAccessorParameterResolver {
    protected InputStreamReader getInputStreamReader() {
        String json = "";
        try {
            json = CharStreams.toString(new InputStreamReader(
                    getClass().getResourceAsStream("/fixtures/config-xqa.json"), Charset.defaultCharset()));

            URL resource = getClass().getResource("/fixtures/xqa-ingest");
            File file = Paths.get(resource.toURI()).toFile();

            json = json.replace("PATH_TO_XQA-INGEST_XML_FILES", file.getAbsolutePath());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new InputStreamReader(IOUtils.toInputStream(json, Charset.defaultCharset()));
    }
}
