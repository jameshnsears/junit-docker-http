package com.github.jameshnsears.junit;

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
        try (InputStream inputStream = JuntDockerHttpExtension.class.getResourceAsStream("/config.properties")) {

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

        /*
    logging.debug("setup: %s", item)
    for fixturename in item.fixturenames:
        if fixturename.startswith(DockerPyWrapper.RECOGNISED_FIXTURE):
            docker_py_wrapper = DockerPyWrapper()
            # no alternative, at moment, other than using ._request
            config = Configuration(item._request.getfixturevalue(fixturename))
            docker_py_wrapper.rm_containers(config.images())
            if docker_py_wrapper.pull(config.images()):
                docker_py_wrapper.start_containers(config)
         */
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        logger.info(String.format("%s", testMethod.getName()));

        /*
    logging.debug("teardown: %s", item)
    if "PYTEST_DOCKER_PY_KEEP_LOGS" not in os.environ:
        for fixturename in item.fixturenames:
            if fixturename.startswith(DockerPyWrapper.RECOGNISED_FIXTURE):
                docker_py_wrapper = DockerPyWrapper()
                docker_py_wrapper.rm_containers(
                    Configuration(item._request.getfixturevalue(fixturename)).images())
         */
    }
}