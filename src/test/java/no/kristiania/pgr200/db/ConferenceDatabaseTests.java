package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceDatabaseTests {

  private Conference conference;
  private ConferenceDao conferenceDao;
  private DataSource dataSource;
  private TestDataSource testDatasource = new TestDataSource();

  @Before
  public void makeConference(){
    this.conference = SampleData.sampleConference();
    this.dataSource = testDatasource.createDataSource();
    this.conferenceDao = new ConferenceDao(dataSource);
  }

  @After
  public void resetConference(){
    this.conference = null;
    testDatasource.dropTables();
  }

  @Test
  public void shouldReturnCorrectConferenceTitle() throws SQLException {
    conferenceDao.create(conference);

    assertThat(conferenceDao.readOne(conference.getId()).getTitle())
            .isEqualTo(conference.getTitle());
  }

  @Test
  public void shouldFindSavedConference() throws SQLException {
    conferenceDao.create(conference);
    assertThat(conferenceDao.readAll())
            .contains(conference);
  }

  @Test
  public void shouldCompareConferenceFieldByField() throws SQLException {
    conferenceDao.create(conference);
    assertThat(conferenceDao.readOne(conference.getId()))
            .isEqualToComparingOnlyGivenFields(conference, "title", "description", "id");
  }

  @Test
  public void shouldUpdateConferenceInTable() throws SQLException {
    conferenceDao.create(conference);
    conferenceDao.updateOneById(new Conference( conference.getId(), "JavaZone", "Conference about Java", "04-12-2018", "06-12-2018"));
    assertThat(conferenceDao.readOne(conference.getId()).getTitle()).isEqualTo("JavaZone");
  }

  @Test
  public void shouldDeleteConferenceInTable() throws SQLException {
    conferenceDao.create(conference);

    assertThat(conferenceDao.readAll()).contains(conference);
    conferenceDao.deleteOneById(conference.getId());
    assertThat(conferenceDao.readAll()).doesNotContain(conference);
  }
}
