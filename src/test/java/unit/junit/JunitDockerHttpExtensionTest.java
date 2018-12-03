package unit.junit;

import com.github.jameshnsears.junit.JuntDockerHttpExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JuntDockerHttpExtension.class)
public class JunitDockerHttpExtensionTest {

    @Test
    void testExtension() {
        Assertions.assertEquals(1, 1);
    }
}
