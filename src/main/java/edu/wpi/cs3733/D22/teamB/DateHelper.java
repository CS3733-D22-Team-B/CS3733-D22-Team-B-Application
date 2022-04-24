package edu.wpi.cs3733.D22.teamB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

  public static String properDate(Date date) {
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

    return String.format("%d %s %d", day, month, year);
  }

  public static void sortDates(ArrayList<String> dates) {
    sortDates(dates, 0, dates.size() - 1);
  }

  private static void sortDates(ArrayList<String> dates, int start, int end) {
    int i = start;
    int j = end;

    String pivot = dates.get(start + (end - start) / 2);

    while (i <= j) {
      while (compareDates(dates.get(i), pivot) < 0) {
        i++;
      }

      while (compareDates(dates.get(j), pivot) > 0) {
        j--;
      }

      if (i <= j) {
        String temp = dates.get(i);
        dates.set(i, dates.get(j));
        dates.set(j, temp);
        i++;
        j--;
      }
    }

    if (start < j) {
      sortDates(dates, start, j);
    }

    if (i < end) {
      sortDates(dates, i, end);
    }
  }

  public static int compareDates(String date1, String date2) {
    String[] date1Parts = date1.split(" ");
    String[] date2Parts = date2.split(" ");

    int year1 = Integer.parseInt(date1Parts[2]);
    int year2 = Integer.parseInt(date2Parts[2]);

    if (year1 == year2) {
      int month1 = getMonthInt(date1Parts[1]);
      int month2 = getMonthInt(date2Parts[1]);

      if (month1 == month2) {
        int day1 = Integer.parseInt(date1Parts[0]);
        int day2 = Integer.parseInt(date2Parts[0]);

        return day1 - day2;
      } else {
        return month1 - month2;
      }
    } else {
      return year1 - year2;
    }
  }

  private static int getMonthInt(String month) {
    switch (month) {
      case "January":
        return 1;
      case "February":
        return 2;
      case "March":
        return 3;
      case "April":
        return 4;
      case "May":
        return 5;
      case "June":
        return 6;
      case "July":
        return 7;
      case "August":
        return 8;
      case "September":
        return 9;
      case "October":
        return 10;
      case "November":
        return 11;
      case "December":
        return 12;
      default:
        return 0;
    }
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
