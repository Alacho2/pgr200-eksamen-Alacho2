package no.kristiania.pgr200.server;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new HttpServerConfig();
        int port = 9010;
        System.out.println("Server listening on port " + port);
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                    new HttpServerRequestHandlerEcho(),
                    new HttpServerRequestHandlerCapi(),
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
