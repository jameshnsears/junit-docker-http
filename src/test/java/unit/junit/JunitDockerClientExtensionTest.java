package unit.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.jameshnsears.junit.JuntDockerClientExtension;

@ExtendWith(JuntDockerClientExtension.class)
class JunitDockerClientExtensionTest {

    //@Test
    void testExtension() {
        Assertions.fail();
    }
}
