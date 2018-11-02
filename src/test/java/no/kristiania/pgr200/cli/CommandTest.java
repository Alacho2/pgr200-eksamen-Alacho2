package no.kristiania.pgr200.cli;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {

    @Test
    public void shouldShouldGetAllCommandFields(){
        Command c = exampleCommand();
        assertThat(c.getName()).isEqualTo("name");
        assertThat(c.getDescription()).isEqualTo("description");
        assertThat(c.getType()).isEqualTo("String");
        assertThat(c.getMode()).isEqualTo("mode");
        assertThat(c.getTable()).isEqualTo("table");
        assertThat(c.getSubQuestionValue()).isEqualTo("none");
        assertThat(c.getSubQuestionName()).isEqualTo(new String[]{"none"});
    }


    public Command exampleCommand(){
        return new Command("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }




}
