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
    public void shouldParseJsonConferenceInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"conference\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"date_start\":\"10-10-2010\",\"date_end\":\"10-10-2018\",\"title\":\"myTitle\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldParseJsonTrackInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"track\",\"fields\":[{\"name\":\"title\",\"value\":\"Track 5\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"track_conference_id\",\"value\":\"1\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody().contains("\"track_conference_id\":7,\"title\":\"Track 5\",\"description\":\"myDescription\""));
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldParseJsonTalkInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"talk\",\"fields\":[{\"name\":\"title\",\"value\":\"talk title\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
    }

    @Test
    @Ignore
    public void shouldParseCapiPathConferenceRetrieve() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":7}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).contains("\"date_start\":\"2010-10-10\",\"date_end\":\"2018-10-10\",\"title\":\"myTitle\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldParseCapiPathTrackRetrieve() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"id\",\"value\":5}]}");
        HttpClientResponse response = request.execute();
        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldParseCapiPathTalkRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"talk\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldFailParseCapiPathTalkRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST", "application/json",
                "");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @Ignore
    public void shouldFailCapiInvalidTable() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"failtable\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @Ignore
    public void shouldParseJsonConferenceDelete() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST","application/json",
                "{\"mode\":\"delete\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":5}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void shouldParseJsonReset() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/reset", "POST", "application/json",
                "{\"mode\":\"reset\"}");
        HttpClientResponse response = request.execute();


    }
}
