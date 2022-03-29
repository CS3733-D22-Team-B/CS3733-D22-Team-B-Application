package edu.wpi.cs3733.D22.teamB;

public class ServiceModel {
    private RequestStatusModel status;
    public ServiceModel(RequestStatusModel st)
    {
        status = st;
    }
    public void setStatus(RequestStatusModel st)
    {
        status = st;
    }
    public String getType()
    {
        return status.getType();
    }
    public RequestStatusModel getStatus()
    {return status;}

}
