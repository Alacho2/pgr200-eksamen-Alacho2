package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.server.HttpClientRequest;
import no.kristiania.pgr200.server.HttpClientResponse;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveUpdate extends CommandHandler {
    public InteractiveUpdate() {
        start("update");
    }

    public InteractiveUpdate(Scanner sc) {
        super(sc);
        start("update");
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
