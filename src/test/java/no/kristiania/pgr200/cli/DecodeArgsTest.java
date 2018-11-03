package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.server.*;
import no.kristiania.pgr200.utils.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class DecodeArgsTest {

    private static HttpServerListener server;
    private int port;
    private String host;

    @BeforeClass
    public static void startServer() throws IOException {
        ParseCommands.parseAllCommands();
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Before
    public void insertAllCommands(){
        port = server.getPort();
        host = "localhost";
    }

    @Test
    public void testInsertCommands() throws IOException {
        DecodeArgs da1 = exampleCommand("-i\r\nconferences\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        DecodeArgs da2 = exampleCommand("-i\r\ntracks\r\ntitle\r\ndescription\r\n1");
        DecodeArgs da3 = exampleCommand("-i\r\ntalks\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");
        assertThat(da1.decode(new String[]{"START"}, port, host)).isNotNull();
        assertThat(da2.decode(new String[]{"START"}, port, host)).isNotNull();
        assertThat(da3.decode(new String[]{"START"}, port, host)).isNotNull();
    }


    @Test
    public void testRetrieveConferenceCommands() throws IOException {
        DecodeArgs da = exampleCommand("-r\r\nconferences\r\ny\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testRetrieveTrackCommands() throws IOException {
        DecodeArgs da = exampleCommand("-r\r\ntracks\r\ny\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testRetrieveTalkCommands() throws IOException {
        DecodeArgs da = exampleCommand("-r\r\ntalks\r\ny\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testUpdateConferenceCommands() throws IOException {
        DecodeArgs da = exampleCommand("-u\r\nconferences\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testUpdateTrackCommands() throws IOException {
        DecodeArgs da = exampleCommand("-u\r\ntracks\r\n1\r\ntitle\r\ndescription\r\n1\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testUpdateTalkCommands() throws IOException {
        DecodeArgs da = exampleCommand("-u\r\ntalks\r\n1\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testDeleteConferenceCommands() throws IOException {
        DecodeArgs da = exampleCommand("-d\r\nconferences\r\n1\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testDeleteTrackCommands() throws IOException {
        DecodeArgs da = exampleCommand("-d\r\ntracks\r\n1\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testDeleteTalkCommands() throws IOException {
        DecodeArgs da = exampleCommand("-d\r\ntalks\r\n1\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }

    @Test
    public void testDeleteWithHelTalkCommands() throws IOException {
        DecodeArgs da = exampleCommand("-d\r\n-h\r\ntalks\r\n1\r\ny");
        assertThat(da.decode(new String[]{"START"},  port, host)).isNotNull();
    }


    private Scanner writeToScanner(String message){
        InputStream in = new ByteArrayInputStream(message.getBytes());
        return new Scanner(in);
    }

    private DecodeArgs exampleCommand(String message){
        Scanner sc = writeToScanner(message);
        return new DecodeArgs(sc);
    }

}
