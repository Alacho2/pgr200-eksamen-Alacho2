package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TalkDatabaseTest {

  private Talk talk;
  private TalkDao talkDao;
  private DataSource dataSource;
  private TestDataSource testDatasource = new TestDataSource();
  private static Random random;
  private TrackDao trackDao;
  private ConferenceDao conferenceDao;
  private Track track;
  private Conference conference;


  @BeforeClass
  public static void makeReady(){
    random = new Random();
  }

  @Before
  public void makeTalk(){
    this.conference = sampleConference();
    this.track = sampleTrack();
    this.talk = sampleTalk();
    this.dataSource = testDatasource.createDataSource();
    this.conferenceDao = new ConferenceDao(dataSource);
    this.trackDao = new TrackDao(dataSource);
    this.talkDao = new TalkDao(dataSource);
  }

  @After
  public void resetTalk(){
    track = null;
    conference = null;
    talk = null;
  }

  private String pickOne(String[] alternatives) {
    return alternatives[random.nextInt(alternatives.length)];
  }

  private Talk sampleTalk() {
    talk = new Talk();
    talk.setTitle(pickOne(new String[] {"Something", "Somewhere", "Someplace", "Location", "Bamalam"})+random.nextInt(200));
    talk.setDescription(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    talk.setTalk_location(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    talk.setTimeslot(pickOne(new String[]{"10:10", "12:10", "04:04", "02:10"}));
    talk.setTalk_track_id(1);
    return talk;
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
  public void shouldFindTalkInDatabase() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);
    assertThat(talkDao.readAll()).contains(talk);
  }

}
