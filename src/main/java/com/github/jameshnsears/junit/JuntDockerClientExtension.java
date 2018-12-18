package com.github.jameshnsears.junit;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import com.github.jameshnsears.docker.DockerClient;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class JuntDockerClientExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(JuntDockerClientExtension.class.getName());
    private final DockerClient dockerClient = new DockerClient();

    private void readProps() {
        // TODO check TODO's! + also make sure that sue json file!=
        final Properties prop = new Properties();
        try (InputStream inputStream = JuntDockerClientExtension.class.getResourceAsStream("/config.properties")) {

            prop.load(inputStream);
            logger.info(prop.getProperty("a", "default"));

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        Preconditions.checkNotNull(context);

        final Method testMethod = context.getRequiredTestMethod();
        readProps();
        logger.info(String.format("%s", testMethod.getName()));
        //dockerClient.startContainers(configurationAccessor);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) {
        Preconditions.checkNotNull(context);

        final Method testMethod = context.getRequiredTestMethod();
        logger.info(String.format("%s", testMethod.getName()));
        //dockerClient.rmContainers(configurationAccessor);
    }
}