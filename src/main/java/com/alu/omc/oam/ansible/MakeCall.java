package com.alu.omc.oam.ansible;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

public class MakeCall implements AnsibleCall
{
    private static Logger       log             = LoggerFactory
                                                        .getLogger(MakeCall.class);
    private COMConfig           config;
    private Action action ;
    private static final String COMMAND = "make ";
    

    public MakeCall(COMConfig config, Action action){
        this.config = config;
        this.action = action;
    }
    @Override
    public String prepare(Ansibleworkspace space)
    {
        space.init(config);
        return COMMAND.concat(action.toString().toLowerCase());
    }

}
