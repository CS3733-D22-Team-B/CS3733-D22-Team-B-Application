package edu.wpi.cs3733.D22.teamB.databases;

import java.time.LocalTime;
import java.util.Date;

public class TestingTime {
  private Date date;
  private LocalTime time;

  public TestingTime(Date date, LocalTime time) {
    this.date = date;
    this.time = time;
  }

  public Date getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }
}
