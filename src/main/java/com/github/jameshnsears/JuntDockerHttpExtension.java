package com.github.jameshnsears;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class JuntDockerHttpExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = LoggerFactory.getLogger(JuntDockerHttpExtension.class.getName());

    private void readProps() {
        Properties prop = new Properties();
        try (InputStream inputStream = JuntDockerHttpExtension.class.getResourceAsStream("/junit-docker-http.properties")) {

            prop.load(inputStream);
            logger.info(prop.getProperty("a", "default"));

        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        readProps();
        logger.info(String.format("%s", testMethod.getName()));
        // use json key value with name of test being a key into the configuration
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        logger.info(String.format("%s", testMethod.getName()));
    }
}