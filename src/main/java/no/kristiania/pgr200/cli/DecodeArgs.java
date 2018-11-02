package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DecodeArgs {
    Scanner sc;

    public DecodeArgs() {
        sc = new Scanner(System.in);
    }

    public DecodeArgs(Scanner sc) {
        this.sc = sc;
    }

    public String decode(String[] args, int port, String hostName) throws IOException {
        StringBuilder sb = new StringBuilder();
        switch(args[0].toUpperCase()){
            case "START":
                OutputHandler.printWelcome();
                return new InteractiveClient(sc, port, hostName).start();
            case "LIST CONFERENCS":
                return new RequestHandler("CONFERENCE","GET").execute(port, hostName);
            case "LIST TRACKS":
                return new RequestHandler("TRACKS","GET").execute(port, hostName);
            case "LIST TALKS":
                return new RequestHandler("TALKS","GET").execute(port, hostName);
            default:
                //do stuff
        }
        return sb.toString();
    }

}
