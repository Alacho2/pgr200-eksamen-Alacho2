package no.kristiania.pgr200.cli;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class parseCommandsTest {

    @BeforeClass
    public static void initCommands(){
        ParseCommands.parseAllCommands();
    }


    @Test
    public void insertAllCommands(){
        List<Command> commands = ParseCommands.parse("conferences", "insert");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("conferences");
            assertThat(c.getMode()).isEqualTo("insert");
            assertThat(c).isInstanceOf(Command.class);
        }

    }

    @Test
    public void shouldParseConferenceCommands(){
        List<Command> commands = ParseCommands.parse("conferences", "insert");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("conferences");
            assertThat(c.getMode()).isEqualTo("insert");
            assertThat(c).isInstanceOf(Command.class);
        }
    }

    @Test
    public void shouldReturnStringAsAlternatives(){
        assertThat(ParseCommands.checkForAlternativeTable("con")).isEqualTo("CONFERENCES?");
        assertThat(ParseCommands.checkForAlternativeTable("tal")).isEqualTo("TALKS?");
        assertThat(ParseCommands.checkForAlternativeTable("tra")).isEqualTo("TRACKS?");
        assertThat(ParseCommands.checkForAlternativeTable("t")).contains("TALKS").contains("TRACKS");
    }

    @Test
    public void shouldReturnNullForNoAlternatives(){
        assertThat(ParseCommands.checkForAlternativeTable("loremIpsumRandom")).isNull();
        assertThat(ParseCommands.checkForAlternativeTable("123")).isNull();
        assertThat(ParseCommands.checkForAlternativeTable("--")).isNull();
    }

    @Test
    public void shouldReturnTrueIfTableExists(){
        assertThat(ParseCommands.checkForTable("conferences")).isTrue();
        assertThat(ParseCommands.checkForTable("talks")).isTrue();
        assertThat(ParseCommands.checkForTable("tracks")).isTrue();
        assertThat(ParseCommands.checkForTable("help")).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowErrIfTableNotExists(){
        ParseCommands.checkForTable("loremIpsum");
        ParseCommands.checkForTable("randomTableThatIsNotDefined");
    }

    @Test
    public void shouldParseHelpCommands(){
        List<Command> commands = ParseCommands.parse("help", "help");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("help");
            assertThat(c.getMode()).isEqualTo("help");
            assertThat(c).isInstanceOf(HelpCommand.class);
        }
    }
}
