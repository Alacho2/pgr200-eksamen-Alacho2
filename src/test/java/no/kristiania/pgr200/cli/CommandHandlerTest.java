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

    @BeforeClass
    public static void initCommands(){
        ParseCommands.parseAllCommands();
    }
/**
    @Test
    public void testInsertConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("conference\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void testInsertTrackCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("track\r\ntitle\r\ndescription\r\n1");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((long)interactiveInsert.getCommandValue("track_conference_id")).isEqualTo(1);
    }

    @Test
    public void testInsertTalkCommands(){
        CommandHandler interactiveInsert = exampleCommandInsert("talk\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("talk_location")).isEqualTo("location");
        assertThat((String)interactiveInsert.getCommandValue("timeslot")).isEqualTo("10:10");
        assertThat((long)interactiveInsert.getCommandValue("talk_track_id")).isEqualTo(1);
    }
*/
    @Test
    public void shouldInsertConference(){
        CommandHandler result = exampleCommandInsert("conference\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat(result.getAllCommands().size()>0);
    }

    @Test
    public void shouldInsertTrack(){
        CommandHandler result = exampleCommandInsert("track\r\ntitle\r\ndescription\r\n1");
        assertThat(result.getAllCommands().size()>0);
    }

    @Test
    public void shouldInsertTalk(){
        CommandHandler result = exampleCommandInsert("talk\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");
        assertThat(result.getAllCommands().size()>0);
    }

    @Test
    public void testRetrieveConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandRetrieve("conference\r\ny\r\ny\r\n");
        assertThat((String)interactiveInsert.getCommandValue("title")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("description")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isNull();
    }

    /*
    @Test
    public void testUpdateConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandUpdate("conference\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void testDeleteConferenceCommands(){
        CommandHandler interactiveInsert = exampleCommandDelete("conference\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
    }*/

    @Test
    public void testHelpCommands(){
        CommandHandler interactiveHelp = exampleCommandHelp("");
        List<Command> commands = interactiveHelp.readAllCommandsByTable("help", "help");
        assertThat(commands.size()>0);
        for(Command c :  commands){
            assertThat(c.getValue()).isNull();
            assertThat(c.getTable()).isEqualTo("help");
            assertThat(c.getMode()).isEqualTo("help");
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getType()).isEqualTo("help");
            assertThat(c.getSubQuestionValue()).isEqualTo("none");
            assertThat(c.getSubQuestionName()[0]).isEqualTo("none");
            assertThat(c.getValue()).isNull();
            assertThat(c).isInstanceOf(HelpCommand.class);
        }
        assertThat(interactiveHelp.readHelpCommands("help", "help")).isNotNull();
    }

    private Scanner writeToScanner(String message){
        InputStream in = new ByteArrayInputStream(message.getBytes());
        return new Scanner(in);
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
    }
}
