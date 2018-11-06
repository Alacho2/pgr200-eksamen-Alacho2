package no.kristiania.pgr200.cli;

import com.google.gson.*;
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
        JsonArray jsonArray = new Gson().fromJson(jsonString, JsonArray.class);
        for(JsonElement j : jsonArray){
            System.out.println(j);
        }

        return "";
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
}
