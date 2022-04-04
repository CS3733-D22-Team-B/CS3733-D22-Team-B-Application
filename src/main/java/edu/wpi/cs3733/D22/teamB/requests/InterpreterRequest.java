package edu.wpi.cs3733.D22.teamB.requests;

public class InterpreterRequest extends Request {
  private String language;

  public InterpreterRequest(String employee, String location, String language) {
    super(employee, location);
    this.language = language;
  }

  public final String createRequestID() {
    return "INT" + getHashCode();
  }

  public final String getType() {
    return "Interpreter";
  }

  public String getLanguage() {
    return language;
  }
}
