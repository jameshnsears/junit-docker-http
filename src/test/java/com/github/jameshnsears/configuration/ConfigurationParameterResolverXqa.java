package com.github.jameshnsears.configuration;

import com.github.jameshnsears.docker.DockerClient;
import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class ConfigurationParameterResolverXqa extends ConfigurationParameterResolver {
    private static final Logger logger = LoggerFactory.getLogger(DockerClient.class);

    protected InputStreamReader getInputStreamReader() {
        String json = "";

        try {
            json = CharStreams.toString(new InputStreamReader(
                    getClass().getResourceAsStream("/fixtures/config-xqa.json"), Charset.defaultCharset()));
            logger.debug(json.toString());

            final File file = Paths.get(getClass().getResource("/fixtures/xqa-ingest").toURI()).toFile();
            logger.debug(file.toString());

            json = json.replace("PATH_TO_XQA-INGEST_XML_FILES", file.getAbsolutePath());
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }

        return new InputStreamReader(IOUtils.toInputStream(json, Charset.defaultCharset()));
    }
}
