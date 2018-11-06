package no.kristiania.pgr200.server;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

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
     public void test10shouldParseJsonConferenceInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"conference\",\"fields\":[{\"name\":\"title\",\"value\":\"myTitle\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"date_start\":\"10-10-2010\",\"date_end\":\"10-10-2018\",\"title\":\"myTitle\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test20shouldParseJsonTrackInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"track\",\"fields\":[{\"name\":\"title\",\"value\":\"Track 5\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"track_conference_id\",\"value\":\"1\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"track_conference_id\":1,\"title\":\"Track 5\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test30shouldParseJsonTalkInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"insert\",\"table\":\"talk\",\"fields\":[{\"name\":\"title\",\"value\":\"talk title\"},{\"name\":\"description\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"talk_location\",\"value\":\"Room 5\"},{\"name\":\"talk_track_id\",\"value\":\"1\"}," +
                        "{\"name\":\"timeslot\",\"value\":\"14:00\"}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);

    }

    @Test
    public void test40shouldParseCapiPathConferenceRetrieve() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).contains("\"date_start\":\"2010-10-10\",\"date_end\":\"2018-10-10\",\"title\":\"myTitle\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test40shouldParseCapiPathConferenceRetrieveAll() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).contains("\"date_start\":\"2010-10-10\",\"date_end\":\"2018-10-10\",\"title\":\"myTitle\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test50shouldParseCapiPathTrackRetrieve() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).contains("\"track_conference_id\":1,\"title\":\"Track 5\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test60shouldParseCapiPathTalkRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"talk\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).contains("talk_track_id\":1,\"timeslot\":\"02:00:00 PM\",\"title\":\"talk title\",\"description\":\"myDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test70shouldFailParseCapiPathTalkRetrieve() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST", "application/json",
                "");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void test80shouldFailCapiInvalidTable() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST","application/json",
                "{\"mode\":\"retrieve\",\"table\":\"failtable\",\"fields\":[{\"name\":\"id\",\"value\":1}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @Ignore
    public void test90shouldParseJsonConferenceDelete() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST","application/json",
                "{\"mode\":\"delete\",\"table\":\"conference\",\"fields\":[{\"name\":\"id\",\"value\":5}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore
    public void test100shouldParseJsonReset() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/reset", "POST", "application/json",
                "{\"mode\":\"reset\"}");
        HttpClientResponse response = request.execute();


    }
    @Test
    public void test61shouldParseJsonConferenceUpdate() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"update\",\"table\":\"conference\",\"fields\":[{\"name\":\"title\",\"value\":\"myNewTitle\"},{\"name\":\"description\"," +
                        "\"value\":\"myNewDescription\"},{\"name\":\"id\",\"value\":\"1\"},{\"name\":\"time-start\",\"value\":\"10-10-2010\"},{\"name\":\"time-end\",\"value\":\"10-10-2018\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"date_start\":\"10-10-2010\",\"date_end\":\"10-10-2018\",\"title\":\"myNewTitle\",\"description\":\"myNewDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test62shouldParseJsonTrackUpdate() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"update\",\"table\":\"track\",\"fields\":[{\"name\":\"title\",\"value\":\"My New Track 5\"},{\"name\":\"description\"," +
                        "\"value\":\"myNewDescription\"},{\"name\":\"id\",\"value\":\"1\"},{\"name\":\"track_conference_id\",\"value\":\"1\"}]}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"track_conference_id\":1,\"title\":\"My New Track 5\",\"description\":\"myNewDescription\"");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test63shouldParseJsonTalkUpdate() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi", "POST", "application/json",
                "{\"mode\":\"update\",\"table\":\"talk\",\"fields\":[{\"name\":\"title\",\"value\":\"New talk title\"},{\"name\":\" newdescription\"," +
                        "\"value\":\"myDescription\"},{\"name\":\"id\",\"value\":\"1\"},{\"name\":\"talk_location\",\"value\":\"Room 5\"},{\"name\":\"talk_track_id\",\"value\":\"1\"}," +
                        "{\"name\":\"timeslot\",\"value\":\"14:00\"}]}");
        HttpClientResponse response = request.execute();

        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);

    }
}
