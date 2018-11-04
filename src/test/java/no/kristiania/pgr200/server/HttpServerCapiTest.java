package no.kristiania.pgr200.server;

import org.junit.BeforeClass;
import org.junit.Ignore;
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
    @Ignore
    public void shouldParseJsonRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    public void shouldParseJsonInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"conferences\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\",\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();


    }
}
