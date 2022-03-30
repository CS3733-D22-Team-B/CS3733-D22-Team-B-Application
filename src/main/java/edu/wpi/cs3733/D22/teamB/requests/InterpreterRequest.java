package edu.wpi.cs3733.D22.teamB.requests;

public class InterpreterRequest extends Request {
  private String language;

  public InterpreterRequest(String employee, String language, String location) {
    super(employee, location);
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }
}
