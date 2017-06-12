package Utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChecksTest {
    @Test
    public void checkPassTest() throws Exception {
        Checks check = new Checks();

        assertTrue("admin and admin must be true", check.checkPass("admin", "admin"));
        assertFalse("empty password must not be false", check.checkPass("rn3232rn3", ""));
        assertFalse("empty login must not be false", check.checkPass("", "f23f23f23"));
        assertFalse("empty login and empty password must be false", check.checkPass("", ""));
    }

}