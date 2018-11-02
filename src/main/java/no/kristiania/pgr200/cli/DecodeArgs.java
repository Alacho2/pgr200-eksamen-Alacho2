package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
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

    public String decode(String[] args, DataSource dataSource) {
        StringBuilder sb = new StringBuilder();
        switch(args[0].toUpperCase()){
            case "START":
                OutputHandler.printWelcome();
                CommandHandler ch = new InteractiveClient(sc).start();
                sb.append(getResult(ch, dataSource));
                break;
            case "LIST":
                ConferenceDao cd = new ConferenceDao();
                try {
                    List<Conference> list =  cd.readAll();
                    list.forEach(s->sb.append(s.toString()+"\n"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                //do stuff
        }
        return sb.toString();
    }


    String getResult(CommandHandler ch, DataSource dataSource){
        try {
            if (ch instanceof InteractiveInsert) {
                return ((InteractiveInsert) ch).execute(dataSource);
            } else if (ch instanceof InteractiveRetrieve) {
                return ((InteractiveRetrieve) ch).execute(dataSource);
            } else if (ch instanceof InteractiveUpdate) {
                return ((InteractiveUpdate) ch).execute(dataSource);
            } else if (ch instanceof InteractiveDelete) {
                return ((InteractiveDelete) ch).execute(dataSource);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "no result";
    }

}