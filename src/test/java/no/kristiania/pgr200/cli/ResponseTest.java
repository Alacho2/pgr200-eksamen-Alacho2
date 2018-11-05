package no.kristiania.pgr200.cli;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    @Test
    public void shouldReturnStatusCode200(){
        Response r = new Response(200, "", "");
        assertThat(r.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReturnStatusCode404(){
        Response r = new Response(404, "", "");
        assertThat(r.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReturnBodyConference(){
        Response r = new Response(200, ExampleData.getJson(ExampleData.getConferenceExample()), "application/json");
        assertThat(r.getBody()).isEqualTo("\"{\\\"date_start\\\":\\\"10-10-2010\\\",\\\"date_end\\\":\\\"10-10-2018\\\",\\\"title\\\":\\\"title\\\",\\\"description\\\":\\\"description\\\",\\\"id\\\":1}\"");
    }

    @Test
    public void shouldReturnBodyCommand(){
        Response r = new Response(200, ExampleData.getJson(ExampleData.getStringINSERTCommand()), "application/json");
        assertThat(r.getBody()).isEqualTo("\"[{\\\"name\\\":\\\"name\\\",\\\"description\\\":\\\"description\\\",\\\"type\\\":\\\"type\\\",\\\"mode\\\":\\\"insert\\\",\\\"table\\\":\\\"track\\\",\\\"subQuestionValue\\\":\\\"none\\\",\\\"subQuestionName\\\":[\\\"none\\\"],\\\"value\\\":\\\"value\\\"}]\"");
    }




}
