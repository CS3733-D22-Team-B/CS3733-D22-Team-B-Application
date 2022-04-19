package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;

public class SanitationRequest extends EquipmentRequest {

    private DatabaseController DC = DatabaseController.getInstance();

    public SanitationRequest(String equipmentID, String information, int priority) {
        super(null, equipmentID, information, priority);
    }
}
