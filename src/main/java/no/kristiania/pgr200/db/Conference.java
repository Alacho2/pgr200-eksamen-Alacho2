package no.kristiania.pgr200.db;

import java.sql.Date;
import java.util.Objects;
import no.kristiania.pgr200.utils.*;

public class Conference {
  private String title, description;
  private int id;
  private String date_start, date_end;

  public Conference(int id, String title, String description, String date_start, String date_end) {
    this.id = id;
    this.title = title;
    this.description = description;
    setDate_start(date_start);
    setDate_end(date_end);
  }

  public Conference() {
  }

  public String getTitle() {
    return title;
  }

  public Conference setTitle(String title) {
    this.title = title;
    return this;
  }


  public String getDescription() {
    return description;
  }

  public Conference setDescription(String description) {
    this.description = description;
    return this;
  }

  public int getId() {
    return id;
  }

  public Conference setId(int id) {
    this.id = id;
    return this;
  }

  public String getDate_start() {
    return date_start;
  }

  /*public Conference setDate_start(String date_start) {
    this.date_start = new Date(DateHandler.convertDateToEpoch(date_start));
    return this;
  } */

  public void setDate_start(String date_start){
    this.date_start = date_start;
  }

  public String getDate_end() {
    return date_end;
  }

  /*public Conference setDate_end(String date_end) {
    this.date_end = new Date(DateHandler.convertDateToEpoch(date_end));
    return this;
  } */

  public void setDate_end(String date_end){
    this.date_end = date_end;
  }
  @Override
  public String toString() {
    return getClass().getSimpleName()+"{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", date_start=" + date_start +
            ", date_end=" + date_end +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Conference that = (Conference) o;
    return id == that.id &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, id);
  }
}
