package no.kristiania.pgr200.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateHandler {

  public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  public static final DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

  public DateHandler(){ }

  public static long convertDateToEpoch(String dateToConvert){
    //Passe inn en dato, få den konvertert til epoch long og returner longen
    long finalTime = 0;
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC+1"));
    try {
      Date date = simpleDateFormat.parse(dateToConvert);
      finalTime = date.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return finalTime;
  }

  public static Time parseTime(String value){
    return Time.valueOf(LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm")));
  }

  public static long convertDateToEpoch(int year, int month, int day){
    LocalDate localDate = LocalDate.of(year, month, day);
    return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
  }
}
