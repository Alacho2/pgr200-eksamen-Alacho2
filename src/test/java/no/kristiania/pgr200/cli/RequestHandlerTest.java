package no.kristiania.pgr200.cli;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {

    @Test
    public void shouldParsePOSTCommands(){
        RequestHandler requestHandler = new RequestHandler(ExampleData.getStringINSERTCommand());
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("POST");
        assertThat(r.getMode()).isEqualTo("insert");
        assertThat(r.getPort()).isEqualTo(0);
        assertThat(r.getId()).isNull();
        assertThat(r.getPath()).isEqualTo("/api/track");
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody()).isEqualTo("{\"mode\":\"insert\",\"table\":\"track\",\"fields\":[{\"name\":\"name\",\"value\":\"value\"}]}");
        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldParseGETCommands(){
        RequestHandler requestHandler = new RequestHandler(ExampleData.getStringGETCommand());
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("GET");
        assertThat(r.getMode()).isEqualTo("retrieve");
        assertThat(r.getPort()).isEqualTo(0);
        assertThat(r.getId()).isNotNull();
        assertThat(r.getId()).isEqualTo(1);
        assertThat(r.getPath()).isEqualTo("/api/track/1");
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody()).isEqualTo("{\"mode\":\"retrieve\",\"table\":\"track\",\"fields\":[{\"name\":\"name\",\"value\":\"value\"},{\"name\":\"id\",\"value\":1}]}");
        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldParseCommand(){
        RequestHandler requestHandler = new RequestHandler("track","retrieve", null);
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("GET");
        assertThat(r.getMode()).isEqualTo("retrieve");
        assertThat(r.getPort()).isEqualTo(0);

    }
}
