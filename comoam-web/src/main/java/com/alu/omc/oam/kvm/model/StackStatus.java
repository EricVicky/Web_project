package com.alu.omc.oam.kvm.model;

import java.util.Date;

import com.alu.omc.oam.config.Action;

public class StackStatus
{
    public static final int IN_USE = 3;
    public static final int IN_ACTION = 2;
    public static final int IDEL = 1;
    Action lastAction;
    Date actionTime;
    int state = IDEL;
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
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
}
