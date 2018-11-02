package no.kristiania.pgr200.db;

import no.kristiania.pgr200.utils.DateHandler;

import java.sql.Time;
import java.util.Objects;

public class Talk {

  private String title, description, talk_location;
  private int id, talk_track_id;
  private Time timeslot;

  public Talk(String title, String description, String talk_location, int id, int talk_track_id, Time timeslot) {
    this.title = title;
    this.description = description;
    this.talk_location = talk_location;
    this.id = id;
    this.talk_track_id = talk_track_id;
    this.timeslot = timeslot;
  }

  public Talk() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTalk_location() {
    return talk_location;
  }

  public void setTalk_location(String talk_location) {
    this.talk_location = talk_location;
  }

  public void setTimeslot(Time timeslot) {
    this.timeslot = timeslot;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTalk_track_id() {
    return talk_track_id;
  }

  public void setTalk_track_id(int talk_track_id) {
    this.talk_track_id = talk_track_id;
  }

  public Time getTimeslot() {
    return timeslot;
  }

  public void setTimeslot(String timeslot) {
    this.timeslot = DateHandler.parseTime(timeslot);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Talk talk = (Talk) o;
    return id == talk.id &&
            talk_track_id == talk.talk_track_id &&
            Objects.equals(title, talk.title) &&
            Objects.equals(description, talk.description) &&
            Objects.equals(talk_location, talk.talk_location) &&
            Objects.equals(timeslot, talk.timeslot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, talk_location, id, talk_track_id, timeslot);
  }

  @Override
  public String toString() {
    return "Talk{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", talk_location='" + talk_location + '\'' +
            ", id=" + id +
            ", talk_track_id=" + talk_track_id +
            ", timeslot=" + timeslot +
            '}';
  }
}
