package no.kristiania.pgr200.server;

import no.kristiania.pgr200.common.PropertyReader;
import no.kristiania.pgr200.db.LocalDataSource;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerBadHttpMethod;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerCapi;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerEcho;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerURL;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static int PORT;
    private static String HOST_NAME;
    private static String WEB_ROOT;
    private static String DEFAULT_FILE;
    private static String SERVER_NAME;
    private static String FILE_NOT_FOUND;
    private static String METHOD_NOT_SUPPORTED;

    public static void main(String[] args) throws IOException {
       new HttpServerConfig();
        int port = 9010;
        DataSource dataSource = new LocalDataSource().createDataSource();
        System.out.println(System.getProperty("user.dir"));
        System.out.println("Server listening on port " + port);
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerCapi(dataSource),
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
