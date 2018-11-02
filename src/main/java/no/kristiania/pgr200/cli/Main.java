package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.db.*;
import no.kristiania.pgr200.utils.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {
    new DbConfig();
    ParseCommands.parseAllCommands();
    DataSource dataSource = new LocalDataSource().createDataSource();

    if(args.length > 0){
      String result = new DecodeArgs().decode(args, dataSource);
      OutputHandler.printResult("RESULT", result); // The Database Response Printed out to the user
    } else {
      OutputHandler.printErrorLine("You must enter use the parameters START or LIST!");
    }

  }
}
