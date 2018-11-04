package no.kristiania.pgr200.server;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerCapiTest {

    private static HttpServerListener server;
    int port = 0;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerCapi(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Test
    public void shouldParseJson() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json", "{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
