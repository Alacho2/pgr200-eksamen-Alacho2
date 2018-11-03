package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandHandlerTest extends CommandHandler {
/**
    @BeforeClass
    public static void initCommands(){
        ParseCommands.parseAllCommands();
    }

    @Test
    public void testInsertConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("conferences\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void testInsertTrackCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("tracks\r\ntitle\r\ndescription\r\n1");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((long)interactiveInsert.getCommandValue("track_conference_id")).isEqualTo(1);
    }

    @Test
    public void testInsertTalkCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("talks\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("talk_location")).isEqualTo("location");
        assertThat((String)interactiveInsert.getCommandValue("timeslot")).isEqualTo("10:10");
        assertThat((long)interactiveInsert.getCommandValue("talk_track_id")).isEqualTo(1);
    }

    @Test
    public void shouldInsertAndMatchConferenceObject(){
        CommandHandler result = exampleCommandInsert("conferences\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((String)result.getCommandValue("title")).isEqualTo((String)result.getCommandValue("title"));
        assertThat((String)result.getCommandValue("description")).isEqualTo((String)result.getCommandValue("description"));
        assertThat(result.getCommandValue("time-start").toString()).isEqualTo("2005-09-10");
        assertThat(result.getCommandValue("time-end").toString()).isEqualTo("2005-09-11");
    }

    @Test
    public void shouldInsertAndMatchTrackObject(){
        Track example = exampleTrack(1, "title", "description", 1);
        CommandHandler result = exampleCommandInsert("tracks\r\ntitle\r\ndescription\r\n1");

        assertThat(example.getTitle()).isEqualTo((String)result.getCommandValue("title"));
        assertThat(example.getDescription()).isEqualTo((String)result.getCommandValue("description"));
        assertThat(Integer.toUnsignedLong(example.getTrack_conference_id())).isEqualTo(result.getCommandValue("track_conference_id"));
    }

    @Test
    public void shouldInsertAndMatchTalkObject(){
        Talk example = exampleTalk(1, "title", "description", "location", "10:10",1);
        CommandHandler result = exampleCommandInsert("talks\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");

        assertThat(example.getTitle()).isEqualTo((String)result.getCommandValue("title"));
        assertThat(example.getDescription()).isEqualTo((String)result.getCommandValue("description"));
        assertThat(example.getTalk_location()).isEqualTo((String)result.getCommandValue("talk_location"));
        assertThat(example.getTimeslot().toString()).isEqualTo("10:10:00");
        assertThat(Integer.toUnsignedLong(example.getTalk_track_id())).isEqualTo(result.getCommandValue("talk_track_id"));
    }

    @Test
    public void testRetrieveConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandRetrieve("conferences\r\ny\r\ny\r\n");
        assertThat((String)interactiveInsert.getCommandValue("title")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("description")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isNull();
    }

    @Test
    public void testUpdateConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandUpdate("conferences\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void testDeleteConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandDelete("conferences\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
    }

    @Test
    public void testHelpCommands(){
        CommandHandler interactiveHelp = exampleCommandHelp("");
        List<Command> commands = interactiveHelp.getAllCommands();
        for(Command c :  commands){
            assertThat(c.getValue()).isNull();
            assertThat(c.getTable()).isNotNull();
            assertThat(c.getMode()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getType()).isNotNull();
            assertThat(c).isInstanceOf(HelpCommand.class);
        }
        assertThat(interactiveHelp.readHelpCommands("help", "help")).isNotNull();
    }

    private Conference exampleConference(int id, String title, String description, String date_start, String date_end){
        Conference c = new Conference();
        c.setId(id);
        c.setTitle(title);
        c.setDescription(description);
        c.setDate_start(date_start);
        c.setDate_end(date_end);
        return c;
    }

    private Talk exampleTalk(int id, String title, String description, String location, String timeslot, int talk_track_id){
        Talk t = new Talk();
        t.setId(id);
        t.setTitle(title);
        t.setDescription(description);
        t.setTalk_location(location);
        t.setTalk_track_id(talk_track_id);
        t.setTimeslot(timeslot);
        return t;
    }

    private Track exampleTrack(int id, String title, String description, int track_conference_id){
        Track t = new Track();
        t.setId(id);
        t.setTitle(title);
        t.setDescription(description);
        t.setTrack_conference_id(track_conference_id);
        return t;
    }

    private Scanner writeToScanner(String message){
        InputStream in = new ByteArrayInputStream(message.getBytes());
        return new Scanner(in);
    }

    private Conference insertCommandToConference(String message){
        CommandHandler interactiveInsert = exampleCommandInsert(message);
        Conference c = new Conference();
        c.setId(1);
        c.setTitle((String)interactiveInsert.getCommandValue("title"));
        c.setDescription((String)interactiveInsert.getCommandValue("description"));
        c.setDate_start((String)interactiveInsert.getCommandValue("time-start"));
        c.setDate_end((String)interactiveInsert.getCommandValue("time-end"));
        return c;
    }

    private CommandHandler exampleCommandInsert(String message){
        Scanner sc = writeToScanner(message);
        return new InteractiveInsert(sc);
    }
    private CommandHandler exampleCommandRetrieve(String message){
        Scanner sc = writeToScanner(message);
        return new InteractiveRetrieve(sc);
    }
    private CommandHandler exampleCommandUpdate(String message){
        Scanner sc = writeToScanner(message);
        return new InteractiveUpdate(sc);
    }
    private CommandHandler exampleCommandDelete(String message){
        Scanner sc = writeToScanner(message);
        return new InteractiveDelete(sc);
    }
    private CommandHandler exampleCommandHelp(String message){
        Scanner sc = writeToScanner(message);
        return new InteractiveHelp();
    }*/
}
