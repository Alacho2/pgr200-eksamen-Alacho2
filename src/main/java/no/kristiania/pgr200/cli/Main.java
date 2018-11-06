package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.server.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
  private static HttpServerListener server;

  public static void main(String[] args) throws IOException {
    new DbConfig();
    ParseCommands.parseAllCommands();
    startServer();

    if(args.length > 0){
      String result = new DecodeArgs().decode(args, server.getPort(), "localhost");
      OutputHandler.printResult("RESULT", result); // The Database Response Printed out to the user
    } else {
      OutputHandler.printErrorLine("You must enter use the parameters START or LIST!");
    }

  }

  public static void startServer() throws IOException {
    server = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                    new HttpServerRequestHandlerEcho(),
                    new HttpServerRequestHandlerCapi(),
                    new HttpServerRequestHandlerURL()),
            new HttpServerParserRequest(),
            new HttpServerWriterResponse()
    );
    server.start(0);
  }
}
