package no.kristiania.pgr200.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class HttpServerRequestHandlerCapi implements HttpServerRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        if(request.getPath().contains("capi")){
            Gson gson = new Gson();
            System.out.println("request json: " + request.getBody());
            HttpServerJsonWrapper jsonQuery = gson.fromJson(request.getBody(), HttpServerJsonWrapper.class);
            System.out.println("mode: " + jsonQuery.getMode());
            System.out.println("table: " + jsonQuery.getTable());
            ArrayList<HttpServerJsonWrapper.Field> jsonFields = (ArrayList<HttpServerJsonWrapper.Field>) jsonQuery.getFields();
            for (HttpServerJsonWrapper.Field field:jsonFields) {
                System.out.println("field name: " + field.getName());
                System.out.println("field value: " + field.getValue());
            }
        }

        return false;
    }
}
