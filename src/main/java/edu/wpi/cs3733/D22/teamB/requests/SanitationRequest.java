package edu.wpi.cs3733.D22.teamB.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;

public class SanitationRequest {

    private DatabaseController DC = DatabaseController.getInstance();
    private MedicalEquipment equipment;
    private String equipmentID;
    private String information;
    private int priority;

    SanitationRequest(String equipmentID, String information, int priority){
        this.equipmentID = equipmentID;
        this.information = information;
        this.priority = priority;
    }

    public MedicalEquipment getEquipment() {
        equipment = DC.getMedicalEquipmentByID(equipmentID);
        return equipment;
    }

    public void setMedicalEquipment(MedicalEquipment medEq){
        setEquipmentID(medEq.getEquipmentID());
    }

    public String getEquipmentID(){
        return this.equipmentID;
    }

    private void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    ////////////// Equipmment //////////////////////////
    public String getType() {
        return equipment.getType();
    }

    public boolean getIsClean() {
        return equipment.getIsClean();
    }

    public String getIsSterilized() {
        return equipment.getIsSterilized();
    }

    public String getAvailability() {
        return equipment.getAvailability();
    }

    public String getName() {
        return equipment.getName();
    }
}
