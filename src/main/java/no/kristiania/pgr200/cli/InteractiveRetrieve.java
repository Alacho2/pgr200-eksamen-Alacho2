package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.server.HttpClientRequest;
import no.kristiania.pgr200.server.HttpClientResponse;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveRetrieve<T> extends CommandHandler {

    public InteractiveRetrieve() {
        start("retrieve");
    }

    public InteractiveRetrieve(Scanner sc) {
        super(sc);
    }

}
