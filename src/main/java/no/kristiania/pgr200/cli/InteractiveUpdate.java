package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
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

    public String execute(DataSource dataSource) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String table = getCommandValue("table").toString();
        OutputHandler.printInfo("Executing Update on "+table);
        switch (table){
            case "conferences":
                ConferenceDao conferenceDao = new ConferenceDao(dataSource);
                Conference c = mapToConference();
                Conference conferenceOriginal = conferenceDao.readOne(c.getId());
                if(conferenceOriginal != null && doneCheck(c, conferenceOriginal)) {
                    conferenceDao.updateOneById(c);
                    sb.append(c.toString());
                }else{
                    sb.append("UPDATE ABORTED!");
                }
                break;
            case "talks":
                TalkDao talkDao = new TalkDao(dataSource);
                Talk t = mapToTalk();
                Talk talkOriginal = talkDao.readOne(t.getId());
                if(talkOriginal != null && doneCheck(t, talkOriginal)) {
                talkDao.updateOneById(t);
                sb.append(t.toString());
                 }else{
                sb.append("UPDATE ABORTED!");
                }
                break;
            case "tracks":
                TrackDao trackDao = new TrackDao(dataSource);
                Track track = mapToTrack();
                Track trackOriginal = trackDao.readOne(track.getId());
                if(trackOriginal != null && doneCheck(track, trackOriginal)) {
                    trackDao.updateOneById(track);
                    sb.append(track.toString());
                }else{
                    sb.append("UPDATE ABORTED!");
                }
                break;
        }
        return sb.toString();
    }




    private Conference mapToConference(){
        Conference conference = new Conference();
        conference.setId(Integer.parseInt(getCommandValue("id").toString()));
        conference.setTitle(getCommandValue("title").toString());
        conference.setDescription(getCommandValue("description").toString());
        conference.setDate_start(getCommandValue("time-start").toString());
        conference.setDate_end(getCommandValue("time-end").toString());
        return conference;
    }

    private Talk mapToTalk(){
        Talk talk = new Talk();
        talk.setTitle(getCommandValue("title").toString());
        talk.setDescription(getCommandValue("description").toString());
        talk.setTalk_location(getCommandValue("talk_location").toString());
        talk.setTimeslot(getCommandValue("timeslot").toString());
        talk.setTalk_track_id(Integer.parseInt(getCommandValue("talk_track_id").toString()));
        return talk;
    }

    private Track mapToTrack(){
        Track track = new Track();
        track.setTitle(getCommandValue("title").toString());
        track.setDescription(getCommandValue("description").toString());
        track.setTrack_conference_id(Integer.parseInt(getCommandValue("track_conference_id").toString()));
        return track;
    }

    /**
     * Display original and the new object and asks if the user want to complete and update it in the database
     */
    <T> boolean doneCheck(T original, T updated){
        OutputHandler.printResult("ORIGINAL", original.toString());
        OutputHandler.printResult("AFTER UPDATE", updated.toString());
        OutputHandler.printCriticalQuestion("Are you sure you want to update");
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
