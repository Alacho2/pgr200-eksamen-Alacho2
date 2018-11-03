package no.kristiania.pgr200.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpServerParserTest {

    @Test
    public void shouldParsePostRequest() throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream input = createInputStream(buildTestPostRequest(sb));
        HttpServerRequest request = new HttpServerParserRequest().parse(input);

        assertThat(request.getHttpMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("capi");
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
        assertThat(request.getHeader("Host")).isEqualTo("localhost");
        assertThat(request.getHeader("Connection")).isEqualTo("close");


    }

    private String buildTestPostRequest(StringBuilder sb) {
        String body = "[{\"command\":\"insert\",\"table\":\"conference\",\"fields\":{\"title\":\"Mobile Era\",\"description\":\"A conference about stuff.\",\"start_date\":\"02/11/2018\",\t\t\t\"end_date\":\"02/11/2018\"}}]\r\n";

        sb.append("POST /capi HTTP/1.1\r\n");
        sb.append("Host: localhost\r\n");
        sb.append("Content-Type: application/json\r\n");
        sb.append("Connection: close\r\n");
        sb.append("Content-Length: " + body.length() + "\r\n");
        sb.append("\r\n");
        sb.append(body);

        return sb.toString();
    }

    private InputStream createInputStream(String testString) {
        return new ByteArrayInputStream(testString.getBytes());
    }



}
