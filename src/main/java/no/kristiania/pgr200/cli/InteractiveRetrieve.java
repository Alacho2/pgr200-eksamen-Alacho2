package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveRetrieve<T> extends CommandHandler {

    public InteractiveRetrieve() {
        start("retrieve");
    }

    public InteractiveRetrieve(Scanner sc) {
        super(sc);
        start("retrieve");
    }

    public String execute(DataSource dataSource) throws SQLException {
        String table = getCommandValue("table").toString();
        OutputHandler.printInfo("Executing retrieve on "+table);
        DataAccessObject model = null;
        switch (table){
            case "conferences":
                model = new ConferenceDao(dataSource);
                break;
            case "talks":
                model = new TalkDao(dataSource);
                break;
            case "tracks":
                model = new TrackDao(dataSource);
                break;
        }
        return read(model);
    }


    /**
     * Returns string result from the request response
     */
    private String read(DataAccessObject<T> ad)throws SQLException{
        StringBuilder sb = new StringBuilder();
        Boolean getAllEntries = checkValue("all entries");
        if(getAllEntries) {
            for (T t : ad.readAll()){
                sb.append(t.toString() + "\n");
            }
        }else if(!getAllEntries){
            T t = ad.readOne((long)getCommandValue("id"));
            sb.append(t.toString()+"\n");
        }
        return sb.toString();
    }

    public Boolean checkValue(String name){
        return getCommandValue(name).equals("TRUE");
    }


}
