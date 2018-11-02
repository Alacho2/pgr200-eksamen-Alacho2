package no.kristiania.pgr200.cli;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    @Test
    public void shouldReturnStatusCode200(){
        Response r = new Response(200, "");
        assertThat(r.getStatusCode()).isEqualTo(200);
    }
    



}
