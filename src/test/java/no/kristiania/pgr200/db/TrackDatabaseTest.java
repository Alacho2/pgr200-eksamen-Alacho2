package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackDatabaseTest {

  private Track track;
  private DataSource dataSource;
  private TestDataSource testDataSource = new TestDataSource();
  private static Random random;
  private TrackDao trackDao;
  private ConferenceDao conferenceDao;
  private Conference conference;


  @BeforeClass
  public static void makeReady(){
    random = new Random();
  }

  @Before
  public void makeTrack(){
    this.track = sampleTrack();
    this.dataSource = testDataSource.createDataSource();
    this.conferenceDao = new ConferenceDao(dataSource);
    this.trackDao = new TrackDao(dataSource);
    this.conference = sampleConference();
  }

  @After
  public void resetTrack(){
    track = null;
    conference = null;
  }

  private String pickOne(String[] alternatives) {
    return alternatives[random.nextInt(alternatives.length)];
  }


  private Track sampleTrack() {
    track = new Track();
    track.setTitle(pickOne(new String[] {"Something", "Somewhere", "Someplace", "Location", "Bamalam"})+random.nextInt(200));
    track.setDescription(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    track.setTrack_conference_id(1);

    return track;
  }

  private Conference sampleConference() {
    conference = new Conference();
    conference.setTitle(pickOne(new String[] { "John", "Paul", "George", "Ringo" }) + random.nextInt(2000));
    conference.setDescription(pickOne(new String[] { "Lennon", "McCartney", "Harrison", "Starr" }));
    conference.setDate_start("09-12-2017");
    conference.setDate_end("09-12-2017");

    return conference;
  }


  @Test
  public void shouldFindSavedTrack() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    assertThat(trackDao.readAll()).contains(track);
  }

}
