package no.kristiania.pgr200.server;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;
import no.kristiania.pgr200.server.controllers.*;
import no.kristiania.pgr200.server.requesthandlers.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HttpServerRESTMethodsTest {


    private static HttpServerListener server;
    int port = 0;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerCapi(),
                        new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Test @Ignore
    public void shouldReturnTalkController(){
        AbstractController controller = new HttpServerRouter().route("capi/talk");
        assertThat(controller).isInstanceOf(TalkController.class);
    }

    @Test @Ignore
    public void shouldReturnTrackController(){
        AbstractController controller = new HttpServerRouter().route("capi/track");
        assertThat(controller).isInstanceOf(TrackController.class);
    }

    @Test @Ignore
    public void shouldReturnConferenceController(){
        AbstractController controller = new HttpServerRouter().route("capi/conference");
        assertThat(controller).isInstanceOf(ConferenceController.class);
    }

    @Test @Ignore
    public void shouldReturnNullController(){
        AbstractController controller = new HttpServerRouter().route("capi/conf");
        assertThat(controller).isNull();
    }

    @Test @Ignore
    public void test10InsertRequestShouldInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST", "application/json",
                "{\"title\":\"My conference\",\"description\":\"About my conference\",\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\",\"title\":\"My conference\",\"description\":\"About my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test @Ignore
    public void test11PutRequestShouldUpdate() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "PUT", "application/json",
                "{\"title\":\"My New conference\",\"description\":\"New about my conference\",\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\",\"title\":\"My New conference\",\"description\":\"New about my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test @Ignore
    public void test12RetrieveRequestShouldRetrieveSingleRow() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"2018-10-09\",\"date_end\":\"2018-10-11\",\"title\":\"My New conference\",\"description\":\"New about my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test @Ignore
    public void test13RetrieveAllRequestShouldRetrieveAll() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test @Ignore
    public void test14DeleteRequestShouldDelete() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"Element with id 1 successfully deleted.\"");
    }
}
