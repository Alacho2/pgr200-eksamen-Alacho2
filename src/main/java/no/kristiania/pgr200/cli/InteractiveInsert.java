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
    }
}
