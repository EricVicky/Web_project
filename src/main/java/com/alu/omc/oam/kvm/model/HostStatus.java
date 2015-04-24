package com.alu.omc.oam.kvm.model;

import java.util.Date;

import com.alu.omc.oam.config.Action;

public class HostStatus
{
    public int IN_USE = 3;
    public int IN_ACTION = 2;
    public int IDEL = 1;
    Action lastAction;
    Date actionTime;
    int state = IDEL;
    
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public Action getLastAction()
    {
        return lastAction;
    }
    public void setLastAction(Action lastAction)
    {
        this.lastAction = lastAction;
    }
    public Date getActionTime()
    {
        return actionTime;
    }
    public void setActionTime(Date actionTime)
    {
        this.actionTime = actionTime;
    }
    
    public HostStatus( Action action, Date actionTime){
        this.lastAction = action;
        this.actionTime = actionTime;
    }
}
