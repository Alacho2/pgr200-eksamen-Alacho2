package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceDatabaseTests {

  private Conference conference;
  private DataSource dataSource;
  private TestDataSource testDatasource = new TestDataSource();
  private static Random random = new Random();

    private Conference sampleConference() {
        conference = new Conference();
        conference.setTitle(pickOne(new String[] { "John", "Paul", "George", "Ringo" }) + random.nextInt(2000));
        conference.setDescription(pickOne(new String[] { "Lennon", "McCartney", "Harrison", "Starr" }));
        conference.setDate_start("09-12-2017");
        conference.setDate_end("09-12-2017");

        return conference;
    }

    @Before
    public void makeConference(){
      conference = sampleConference();
      this.dataSource = testDatasource.createDataSource();
    }

    @After
    public void resetConference(){
      conference = null;
    }

    private String pickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }


    @Test
    public void shouldReturnCorrectConferenceTitle() throws SQLException {
      ConferenceDao conferenceDao = new ConferenceDao(dataSource);
      Conference conference = sampleConference();

      conferenceDao.create(conference);
      assertThat(conferenceDao.readOne(conference.getId()).getTitle()).isEqualTo(conference.getTitle());
    }

    @Test
    public void shouldFindSavedConference() throws SQLException {
        ConferenceDao conferenceDao = new ConferenceDao(dataSource);
        conferenceDao.create(conference);
        assertThat(conferenceDao.readAll())
                .contains(conference);
    }

    @Test
    public void shouldCompareConferenceFieldByField() throws SQLException {
        ConferenceDao conferenceDao = new ConferenceDao(testDatasource.createDataSource());
        conferenceDao.create(conference);
        assertThat(conferenceDao.readAll())
                .contains(conference);
    }

    @Test
    public void shouldUpdateConferenceInTable() throws SQLException {
      ConferenceDao conferenceDao = new ConferenceDao(dataSource);
      conferenceDao.create(conference);
      conferenceDao.updateOneById(new Conference( conference.getId(), "JavaZone", "Conference about Java", "04-12-2018", "06-12-2018"));
      assertThat(conferenceDao.readOne(conference.getId()).getTitle()).isEqualTo("JavaZone");
    }

    @Test
    public void shouldDeleteConferenceInTable() throws SQLException {
      ConferenceDao conferenceDao = new ConferenceDao(dataSource);
      conferenceDao.create(conference);

      assertThat(conferenceDao.readAll()).contains(conference);
      conferenceDao.deleteOneById(conference.getId());
      assertThat(conferenceDao.readAll()).doesNotContain(conference);
    }

}
