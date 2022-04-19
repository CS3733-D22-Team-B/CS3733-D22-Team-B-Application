package edu.wpi.cs3733.D22.teamB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
  public static String stringify(Date date) {
    String day = extractDate(date, true);
    String time = extractTime(date);

    return String.format("%s at %s", day, time);
  }

  public static String extractDate(Date date, boolean dmy) {
    if (dmy) {
      int day = date.getDate();
      String month;

      switch (date.getMonth()) {
        default:
        case 0:
          month = "JAN";
          break;
        case 1:
          month = "FEB";
          break;
        case 2:
          month = "MAR";
          break;
        case 3:
          month = "APR";
          break;
        case 4:
          month = "MAY";
          break;
        case 5:
          month = "JUN";
          break;
        case 6:
          month = "JUL";
          break;
        case 7:
          month = "AUG";
          break;
        case 8:
          month = "SEP";
          break;
        case 9:
          month = "OCT";
          break;
        case 10:
          month = "NOV";
          break;
        case 11:
          month = "DEC";
          break;
      }

      int year = date.getYear() + 1900;

      return String.format("%02d %s %d", day, month, year);
    } else {
      int day = date.getDate();
      String month;

      switch (date.getMonth()) {
        default:
        case 0:
          month = "January";
          break;
        case 1:
          month = "February";
          break;
        case 2:
          month = "March";
          break;
        case 3:
          month = "April";
          break;
        case 4:
          month = "May";
          break;
        case 5:
          month = "June";
          break;
        case 6:
          month = "July";
          break;
        case 7:
          month = "August";
          break;
        case 8:
          month = "September";
          break;
        case 9:
          month = "October";
          break;
        case 10:
          month = "November";
          break;
        case 11:
          month = "December";
          break;
      }

      int year = date.getYear() + 1900;

      return String.format("%s %d %d", month, day, year);
    }
  }

  public static String extractTime(Date date) {
    int hour = date.getHours() % 12;
    hour = (hour == 0) ? 12 : hour;
    int minutes = date.getMinutes();
    String period = date.getHours() < 12 ? "AM" : "PM";

    return String.format("%d:%02d %s", hour, minutes, period);
  }

  public static String csvDateToDate(String csvDate) {
    String year = csvDate.substring(0, 4);
    String month = csvDate.substring(5, 7);
    String day = csvDate.substring(8, 10);
    String hour = csvDate.substring(11, 13);
    String minute = csvDate.substring(14, 16);
    String second = csvDate.substring(17, 19);
    String millisecond = csvDate.substring(20);

    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S", Locale.ENGLISH);
    LocalDateTime date = LocalDateTime.parse(csvDate, formatter);

    return date.toString();
  }
}
