package no.kristiania.pgr200.server;

import java.io.IOException;

public class HttpServerRequestHandlerTalk implements HttpServerRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {
        return false;
    }
}
