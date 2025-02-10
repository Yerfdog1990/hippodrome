import static java.util.concurrent.TimeUnit.SECONDS;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
  @Test
  @Disabled
  @Timeout(value = 22, unit = SECONDS)
  @DisplayName("Timeout test")
  public void timeoutTest() throws Exception {
    Main.main(null);
    System.out.println("This test should timeout.");
  }
}
