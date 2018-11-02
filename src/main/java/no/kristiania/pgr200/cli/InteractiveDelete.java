package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveDelete extends CommandHandler {
    public InteractiveDelete() {
        start("delete");
    }

    public InteractiveDelete(Scanner sc) {
        super(sc);
        start("delete");
    }

    public String execute(DataSource dataSource) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String table = getCommandValue("table").toString();
        long id = Long.parseLong(getCommandValue("id").toString());
        OutputHandler.printInfo("Executing Delete on "+table);
        switch (table){
            case "conferences":
                ConferenceDao conferenceDao = new ConferenceDao(dataSource);
                Conference c = conferenceDao.readOne(id);
                if(c != null && doneCheck(c)) {
                    conferenceDao.deleteOneById(id);
                    sb.append("DELETE COMPLETE");
                }else{
                    sb.append("DELETE ABORTED");
                }
                break;
            case "talks":
                TalkDao talkDao = new TalkDao(dataSource);
                Talk t = talkDao.readOne(id);
                if(t != null && doneCheck(t)) {
                    talkDao.deleteOneById(id);
                    sb.append("DELETE COMPLETE");
                }else{
                    sb.append("DELETE ABORTED");
                }
                break;
            case "tracks":
                TrackDao trackDao = new TrackDao(dataSource);
                Track track = trackDao.readOne(id);
                if(track != null && doneCheck(track)) {
                    trackDao.deleteOneById(id);
                    sb.append("DELETE COMPLETE");
                }else{
                    sb.append("DELETE ABORTED");
                }
                break;
        }
        return sb.toString();
    }

    /**
     * Asks the user to complete or discard the delete
     */
    <T> boolean doneCheck(T original){
        OutputHandler.printResult("OBJECT", original.toString());
        OutputHandler.printCriticalQuestion("Are you sure you want to delete");
        Object value = ScannerHandler.scanInput(sc.nextLine());
        if(value instanceof String){
            if((((String) value).toUpperCase()).contains("Y")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
