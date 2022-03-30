package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class RequestController {
    protected Label requestLabel;
    protected TextField employeeNameInput;
    protected ComboBox<String> floorInput;
    protected ComboBox<String> roomInput;

    protected String employeeName;
    protected String floor;
    protected String room;

    public abstract Boolean setStatus();

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setFloor(String floor) {
        this.floor = floor;
        resetRoomBox();
    }

    public void setRoom(String room) {
        this.room = room;
    }

    private void resetRoomBox() {
        roomInput.getItems().clear();
    }

    public abstract void setRequest();

    public abstract void reset();
}
