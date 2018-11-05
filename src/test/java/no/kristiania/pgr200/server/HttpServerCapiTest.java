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
    public void shouldParseJsonConferenceRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    @Ignore
    public void shouldParseTrackJsonRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    @Ignore
    public void shouldParseTalkJsonRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"talk\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    public void shouldParseJsonConferenceInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"conference\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\",\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    @Ignore
    public void shouldParseJsonTrackInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"track\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\",\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    @Ignore
    public void shouldParseJsonTalkInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"track\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\",\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    public void shouldParseJsonReset() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/reset", "POST", "application/json",
                "{\"mode\":\"reset\"}");
        HttpClientResponse response = request.execute();


    }
}
