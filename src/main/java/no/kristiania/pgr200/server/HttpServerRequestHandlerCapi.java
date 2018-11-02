package no.kristiania.pgr200.server;

import java.io.IOException;

public class HttpServerRequestHandlerCapi implements HttpServerRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        if(request.getPath().contains("capi")){

        }

        return false;
    }
}
