package edu.wpi.cs3733.D22.teamB.requests;

public class InterpreterRequest extends Request {
  private String language;

  public InterpreterRequest(String locationID, String language) {
    super(locationID, "Interpreter");
    this.language = language;
    setInformation();
  }

  public final String createRequestID() {
    return "INT" + getHashCode();
  }

  public final void setInformation() {
    information = "Language: " + language;
  }

  public String getLanguage() {
    return language;
  }
}
