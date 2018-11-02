package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.utils.ScannerHandler;
import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import java.util.Scanner;

public class InteractiveClient {
    Scanner sc;


    public InteractiveClient(){
        sc = new Scanner(System.in);
    }

    public InteractiveClient(Scanner sc){
        this.sc  = sc;
    }

    CommandHandler start(){
        OutputHandler.printQuestion("Pleas enter the mode you want to enter...");
        Object mode = ScannerHandler.scanInput(sc.nextLine());
        if(mode instanceof String) {
            return getMode((String)mode);
        } else{
          return start();
        }
    }

    /**
     * Checks the input parameter value to choose the right mode. If invalid mode is passed then it throws exception.
     * @param mode
     */
    CommandHandler getMode(String mode){
            try {
                switch (mode) {
                    case "INSERT":
                        return new InteractiveInsert(sc);
                    case "RETRIEVE":
                        return new InteractiveRetrieve(sc);
                    case "UPDATE":
                        return new InteractiveUpdate(sc);
                    case "DELETE":
                        return new InteractiveDelete(sc);
                    default:
                        throw new IllegalArgumentException("You must enter a valid mode! (-h) for help");
                }
            }catch (IllegalArgumentException e) {
                OutputHandler.printErrorLine(e.getMessage());
                OutputHandler.printModesLine();
                start(); // restarts the interactive client
            }
            return null;
    }


}
