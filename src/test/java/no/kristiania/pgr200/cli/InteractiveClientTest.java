package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class InteractiveClientTest {
/**
    @Before
    public void insertAllCommands(){
        ParseCommands.parseAllCommands();
    }

    @Test
    public void testInsertCommands(){
        CommandHandler interactiveInsert = exampleCommand("-i\r\nconferences\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void shouldInsertAndMatchConferenceObject(){
        Conference example = exampleConference(1, "title", "description", "10-09-2005", "11-09-2005");
        Conference result = insertCommandToConference("-i\r\nconferences\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");

        assertThat(example).isEqualToComparingOnlyGivenFields(result,"id", "title", "description");

    }

    @Test
    public void testRetrieveCommands(){
        CommandHandler interactiveInsert = exampleCommand("-r\r\nconferences\r\ny\r\ny\r\n");
        assertThat((String)interactiveInsert.getCommandValue("title")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("description")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isNull();
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isNull();
    }

    @Test
    public void testUpdateCommands(){
        CommandHandler interactiveInsert = exampleCommand("-u\r\nconferences\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
        assertThat((String)interactiveInsert.getCommandValue("title")).isEqualTo("title");
        assertThat((String)interactiveInsert.getCommandValue("description")).isEqualTo("description");
        assertThat((String)interactiveInsert.getCommandValue("time-start")).isEqualTo("10-09-2005");
        assertThat((String)interactiveInsert.getCommandValue("time-end")).isEqualTo("11-09-2005");
    }

    @Test
    public void testDeleteCommands(){
        CommandHandler interactiveInsert = exampleCommand("-d\r\nconferences\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat((long)interactiveInsert.getCommandValue("id")).isEqualTo(1);
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

    private Scanner writeToScanner(String message){
        InputStream in = new ByteArrayInputStream(message.getBytes());
        return new Scanner(in);
    }

    private Conference insertCommandToConference(String message){
        CommandHandler interactiveInsert = exampleCommand(message);
        Conference c = new Conference();
        c.setId(1);
        c.setTitle((String)interactiveInsert.getCommandValue("title"));
        c.setDescription((String)interactiveInsert.getCommandValue("description"));
        c.setDate_start((String)interactiveInsert.getCommandValue("time-start"));
        c.setDate_end((String)interactiveInsert.getCommandValue("time-end"));
        return c;
    }

    private String exampleCommand(String message) throws IOException {
        Scanner sc = writeToScanner(message);
        return new InteractiveClient(sc, 0).start();
    }*/

}
