package no.kristiania.pgr200.server;

import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerBadHttpMethod;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerCapi;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerEcho;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerURL;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new HttpServerConfig();
        int port = 9010;
        System.out.println("Server listening on port " + port);
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerCapi(),
                    new HttpServerRequestHandlerBadHttpMethod(),
                    new HttpServerRequestHandlerEcho(),
                    new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        try {
            listener.start(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
