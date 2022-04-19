package edu.wpi.cs3733.D22.teamB;

import java.util.Date;

public class DateHelper {
  public static String stringify(Date date) {
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
    int hour = date.getHours() % 12;
    hour = (hour == 0) ? 12 : hour;
    int minutes = date.getMinutes();
    String period = date.getHours() < 12 ? "AM" : "PM";

    return String.format("%02d %s %d at %02d:%02d %s", day, month, year, hour, minutes, period);
  }
}
