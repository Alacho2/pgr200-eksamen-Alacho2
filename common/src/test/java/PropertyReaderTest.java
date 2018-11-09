import no.kristiania.pgr200.common.PropertyReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PropertyReaderTest {

  PropertyReader pr;

  @Before
  public void getTestsReady() throws IOException {
    pr  = new PropertyReader("/innlevering.properties");

  }

  @Test
  public void shouldCheckForExceptionInProperties() {
    assertThatThrownBy(() -> {
      new PropertyReader(null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Path is not valid.");
  }

  @Test
  public void shouldReturnPropertyHello() {
    assertThat(pr.getProperty("Hello")).isEqualTo("Alexander");
  }

  @Test
  public void shouldSetProperty() {
    assertThat(pr.getProperty("Hello")).isEqualTo("Alexander");
    pr.setProperty("Hello", "new value");
    assertThat(pr.getProperty("Hello")).isNotEqualTo("Alexander");
  }
}
