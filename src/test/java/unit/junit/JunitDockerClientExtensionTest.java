package unit.junit;

import com.github.jameshnsears.junit.JuntDockerClientExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JuntDockerClientExtension.class)
class JunitDockerClientExtensionTest {

    @Test
    void testExtension() {
        Assertions.fail();
    }
}
