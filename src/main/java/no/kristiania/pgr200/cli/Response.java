package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.kristiania.pgr200.server.HttpClientResponse;

import java.util.Objects;

public class Response {
    int statusCode;
    String body;

    public Response(HttpClientResponse httpClientResponse) {
        setStatusCode(httpClientResponse.getStatusCode());
        setBody(httpClientResponse.getBody());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    private void setBody(String body) {
        Gson gson = new GsonBuilder().create();
        this.body = gson.toJson(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return statusCode == response.statusCode &&
                Objects.equals(body, response.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, body);
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                '}';
    }
}
