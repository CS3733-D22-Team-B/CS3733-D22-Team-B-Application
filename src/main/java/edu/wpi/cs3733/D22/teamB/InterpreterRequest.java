package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.Location;

public class InterpreterRequest implements ServiceRequest{
    private String language;
    private Location room;
    public InterpreterRequest(String language, String floor, String room)
    {
        this.language = language;

    }
}
