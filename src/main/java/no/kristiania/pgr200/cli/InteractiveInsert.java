package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveInsert extends CommandHandler {

    public InteractiveInsert() {
        start("insert");
    }

    public InteractiveInsert(Scanner sc) {
        super(sc);
        start("insert");
    }

    public String execute(DataSource dataSource) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String table = getCommandValue("table").toString();
        OutputHandler.printInfo("Executing insert on "+table);
        switch (table){
            case "conferences":
                ConferenceDao conferenceDao = new ConferenceDao(dataSource);
                Conference c = mapToConference();
                conferenceDao.create(c);
                sb.append(c.toString());
                break;
            case "talks":
                TalkDao talkDao = new TalkDao(dataSource);
                Talk talk = mapToTalk();
                talkDao.create(talk);
                sb.append(talk.toString());
                break;
            case "tracks":
                TrackDao trackDao = new TrackDao(dataSource);
                Track track = mapToTrack();
                trackDao.create(track);
                sb.append(track.toString());
                break;
            default:
                sb.append("SOMETHING WENT WRONG NO TABLE MATCH IN SWITCH IN: "+getClass().getSimpleName());
        }
        return sb.toString();
    }

    private Conference mapToConference(){
        Conference conference = new Conference();
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
}
