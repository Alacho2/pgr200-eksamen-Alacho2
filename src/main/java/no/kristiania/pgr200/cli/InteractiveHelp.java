package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.utils.*;

import java.util.List;
import java.util.Scanner;
import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

public class InteractiveHelp extends CommandHandler {

    public InteractiveHelp() {
        startHelp("help");
        System.out.println(execute());
    }

    public InteractiveHelp(Scanner sc) {
        super(sc);
        startHelp("help");
        System.out.println(execute());
    }

    public String execute() {
        List<Command> commands = getAllCommands();
        StringBuilder sb = new StringBuilder();
        sb.append(OutputHandler.printCommandHelpHeader());
        for(Command c : commands){
            sb.append(OutputHandler.printCommandHelpLineWithSpaces(c.getName(),c.getDescription(), c.getType()));
        }
        return sb.toString();
    }

    @Override
    void chooseTable() {
        insertCommands("help", "help");
    }

}
