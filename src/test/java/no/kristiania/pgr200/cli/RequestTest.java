package no.kristiania.pgr200.cli;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    public void shouldReturnConferencePathWithID(){
        Request r = new Request<>("localhost", 0, ExampleData.getStringGETCommand());
        assertThat(r.getPath()).isEqualTo("/api/track/1");
        assertThat(r.getId()).isNotNull();

        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldReturnConferencePathWithNoID(){
        Request r = new Request<>("localhost", 0, ExampleData.getStringINSERTCommand());
        assertThat(r.getPath()).isEqualTo("/api/track");
        assertThat(r.getId()).isNull();
        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldReturnGETRequest(){
        Request r = new Request<>("localhost", 0, ExampleData.getStringGETCommand());
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
    public void shouldReturnPOSTRequest(){
        Request r = new Request<>("localhost", 0, ExampleData.getStringINSERTCommand());
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
}
