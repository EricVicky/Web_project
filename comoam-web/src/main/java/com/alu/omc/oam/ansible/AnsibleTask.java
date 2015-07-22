package com.alu.omc.oam.ansible;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;


public class AnsibleTask {
    public Action getAction()
    {
        return action;
    }
    public void setAction(Action action)
    {
        this.action = action;
    }
    public COMConfig getConfig()
    {
        return config;
    }
    public void setConfig(COMConfig config)
    {
        this.config = config;
    }
    Action action;
    COMConfig config;
    public AnsibleTask(Action action, COMConfig config){
        this.action = action;
        this.config = config;
    }
}