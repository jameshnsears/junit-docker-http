package com.github.jameshnsears;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@ExtendWith(JuntDockerHttpExtension.class)
public class JunitDockerHttpExtensionTest {

    @ParameterizedTest
    @ValueSource(strings = {"Hello"})
    void testExtension(String message) {
        Assertions.assertEquals(1, 1);
    }
}
