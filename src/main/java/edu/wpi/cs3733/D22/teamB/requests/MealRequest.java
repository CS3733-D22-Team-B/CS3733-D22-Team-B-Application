package edu.wpi.cs3733.D22.teamB.requests;

public class MealRequest extends Request {

  public MealRequest(String locationID, String information) {
    super(locationID, "Meal");
    this.information = "Meal: " + information;
  }

  public final String createRequestID() {
    return "MEL" + getHashCode();
  }

}
