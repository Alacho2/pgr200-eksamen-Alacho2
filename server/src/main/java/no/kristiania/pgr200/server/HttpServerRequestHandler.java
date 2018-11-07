package no.kristiania.pgr200.server;

import java.io.IOException;

public interface HttpServerRequestHandler {

    boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException;

}
