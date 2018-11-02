package no.kristiania.pgr200.cli;


import no.kristiania.pgr200.server.HttpClientRequest;
import no.kristiania.pgr200.server.HttpClientResponse;
import no.kristiania.pgr200.utils.*;


import java.io.IOException;
import java.util.Scanner;

public class InteractiveInsert extends CommandHandler {

    public InteractiveInsert() {
        start("insert");
    }

    public InteractiveInsert(Scanner sc) {
        super(sc);
        start("insert");
    }

    public String execute(int port) throws IOException {
        StringBuilder sb = new StringBuilder();
        String table = getCommandValue("table").toString();
        OutputHandler.printInfo("Executing insert on "+table);
        HttpClientRequest request = new HttpClientRequest("localhost", port, "/", "POST", getJson());
        HttpClientResponse response = request.execute();
        sb.append(response.getStatusCode());
        sb.append(response.getBody());
        return sb.toString();
    }

}
