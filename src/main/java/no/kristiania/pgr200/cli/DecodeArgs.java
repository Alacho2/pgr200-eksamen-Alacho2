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
    StringBuilder sb;

    public DecodeArgs() {
        sc = new Scanner(System.in);
        sb = new StringBuilder();
    }

    public DecodeArgs(Scanner sc) {
        this.sc = sc;
        sb = new StringBuilder();
    }

    public String decode(String[] args, int port, String hostName) throws IOException {
        switch(args[0].toUpperCase()){
            case "START":
                OutputHandler.printWelcome();
                return new InteractiveClient(sc, port, hostName).start();
            case "LIST":
                if(args.length>1) return handleListCommand(args, port, hostName);
                break;
            case "RESET":
                return new RequestHandler("RESET", "RESET").execute(port, hostName);
            default:
                //do stuff
        }
        return sb.toString();
    }

    private String handleListCommand(String[] args, int port, String hostName) throws IOException {
        Number id = null;
        if(args.length>2){
            id = Integer.parseInt(args[2]);
        }
        switch (args[1].toUpperCase()) {
            case "CONFERENCE":
                return new RequestHandler("CONFERENCES", "RETRIEVE", id).execute(port, hostName);
            case "TRACK":
                return new RequestHandler("TRACKS", "RETRIEVE", id).execute(port, hostName);
            case "TALK":
                return new RequestHandler("TALK", "RETRIEVE", id).execute(port, hostName);
        }
        return null;
    }

}
