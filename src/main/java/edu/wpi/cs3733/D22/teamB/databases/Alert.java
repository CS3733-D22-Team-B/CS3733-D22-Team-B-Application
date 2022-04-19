package edu.wpi.cs3733.D22.teamB.databases;

public class Alert {
    private String alertID;
    private String locationID;
    private Location location;
    private String type;
    private String isRead;

    DatabaseController DC = DatabaseController.getInstance();

    Alert(String alertID, String locationID, String type, String isRead) {
        this.alertID = alertID;
        this.locationID = locationID;
        this.type = type;
        this.isRead = isRead;
    }

    public String getAlertID() {
        return alertID;
    }

    private void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public Location getLocation() {
        location = DC.getLocationByID(getLocationID());
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    ////////////////////////// Location /////////////////////////////
    public int getXCoord() {
        return location.getXCoord();
    }

    public int getYCoord() {
        return location.getYCoord();
    }

    public String getFloor() {
        return location.getFloor();
    }

    public String getBuilding() {
        return location.getBuilding();
    }

    public String getNodeType() {
        return location.getNodeType();
    }

    public String getLongName() {
        return location.getLongName();
    }

    public String getShortName() {
        return location.getShortName();
    }
}
