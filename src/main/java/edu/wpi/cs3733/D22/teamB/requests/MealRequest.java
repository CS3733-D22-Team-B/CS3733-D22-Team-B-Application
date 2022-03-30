package edu.wpi.cs3733.D22.teamB.requests;

public class MealRequest extends Request {
  String meal;
  public MealRequest(String employee, String location, String meal) {
    super(employee, location);
    this.meal = meal;
  }
}
