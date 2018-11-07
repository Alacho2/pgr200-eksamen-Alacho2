package no.kristiania.pgr200.db;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;

public class LocalDataSource {

  private DataSource dataSource;

  public LocalDataSource(){
    this.dataSource = createDataSource();
  }

  public DataSource createDataSource(){
    PGPoolingDataSource dataSource = new PGPoolingDataSource();
    dataSource.setUrl(DbConfig.URL);
    dataSource.setUser(DbConfig.USER);
    dataSource.setPassword(DbConfig.PASSWORD);

    Flyway flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.migrate();
    return dataSource;
  }
}
