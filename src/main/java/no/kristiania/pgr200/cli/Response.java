package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import no.kristiania.pgr200.server.HttpClientResponse;

import java.util.Objects;

public class Response {
    private int statusCode;
    private String body, content_type;

    public Response(HttpClientResponse httpClientResponse) {
        setContent_type(httpClientResponse.getHeader("Content-Type"));
        setStatusCode(httpClientResponse.getStatusCode());
        setBody(httpClientResponse.getBody());
    }

    public Response(int statusCode, String body, String content_type) {
        setContent_type(content_type);
        setStatusCode(statusCode);
        setBody(body);
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
        if(getContent_type() != null && getContent_type().equals("application/json")) {
            this.body = toPrettyJson(body);
        }else{
            this.body = body;
        }
    }

    public static String toPrettyJson(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
}
