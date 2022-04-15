package edu.wpi.cs3733.D22.teamB.requests;

import java.util.Date;

public class SecurityRequest extends Request {

    public SecurityRequest(String locationID, String information, int priority) {
        super(locationID, null, information, priority, "SECURITY");
        this.information = information;
    }

    public SecurityRequest(
            String requestID,
            String employeeID,
            String locationID,
            String patientID,
            String type,
            String status,
            int priority,
            String information,
            Date timeCreated,
            Date lastEdited) {
        super(
                requestID,
                employeeID,
                locationID,
                patientID,
                type,
                status,
                priority,
                information,
                timeCreated,
                lastEdited);
    }

    public final String createRequestID() { return "SEC" + getHashCode(); }
}
