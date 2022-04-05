package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.Location;

public class MealRequest extends Request {
  String meal;

  public MealRequest(String locationID, String meal) {
    super(locationID, "Meal");
    this.meal = meal;
    setInformation();
  }

  public final String createRequestID() {
    return "MEL" + getHashCode();
  }

  public final void setInformation() {
    information = "Meal: " + meal;
  }

  public final String getMeal() {
    return meal;
  }
}
