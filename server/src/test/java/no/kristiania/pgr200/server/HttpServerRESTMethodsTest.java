package no.kristiania.pgr200.server;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;
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
    public void InsertRequestShouldInsert() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST", "application/json",
                "{\"title\":\"My conference\",\"description\":\"About my conference\",\"time-start\":\"09-10-2018\",\"time-end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"title\":\"My conference\",\"description\":\"About my conference\",\"time-start\":\"09-10-2018\",\"time-end\":\"11-10-2018\"}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void PutRequestShouldUpdate() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "PUT", "application/json",
                "{\"title\":\"My New conference\",\"description\":\"New about my conference\",\"time-start\":\"09-10-2018\",\"time-end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"title\":\"My New conference\",\"description\":\"New about my conference\",\"time-start\":\"09-10-2018\",\"time-end\":\"11-10-2018\"}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void RetrieveRequestShouldRetrieveSingleRow() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"title\":\"My New conference\",\"description\":\"New about my conference\",\"time-start\":\"09-10-2018\",\"time-end\":\"11-10-2018\"}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void RetrieveAllRequestShouldRetrieveAll() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void DeleteRequestShouldDelete() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"Element with id 1 successfully deleted.\"");
    }
}
