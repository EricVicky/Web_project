package com.alu.omc.oam.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.PlaybookCall;
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.Environment;

@Component
public class CommandProtype
{
@Resource
private JsonDataSource dataSource;
public ICommandExec create(String command, File workingdir){
    if(SystemUtils.IS_OS_WINDOWS){
        return new MockCommandExec(command, null,
                            null, workingdir);
    }else{
        return new CommandExec(command, null,
                            null, workingdir);
    }
}
public ICommandExec create(String command, File workingdir, Environment env, Action action){
    Map<String, String> envs = null;
    
    if(SystemUtils.IS_OS_WINDOWS){
        return new MockCommandExec(command, null,
                            null, workingdir);
    }else{
       return new DefaultCommandExecutor(command, workingdir, envs); 
    }
}
public ICommandExec create(PlaybookCall playbookCall, File runDir){
    Map<String, String> envs = new HashMap<String, String>();
    try
    {
        envs.putAll( EnvironmentUtils.getProcEnvironment());
    }
    catch (IOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    if(SystemUtils.IS_OS_WINDOWS){
        return new MockCommandExec(playbookCall.asCommand(),
                new String[]{playbookCall.getAction().name(), playbookCall.getConfig().getEnvironment().name(),playbookCall.getConfig().getCOMType().name()},
                            null, runDir);
    }else{
       if(playbookCall.getConfig().getEnvironment() == Environment.OPENSTACK){
           envs.putAll(dataSource.getOpenstackConfig().asEnvironmentMap());
           envs.put("PWD", runDir.getAbsolutePath());
       }
       return new DefaultCommandExecutor(playbookCall.asCommand(), runDir, envs); 
    }
}


public ICommandExec create1(PlaybookCall playbookCall, File runDir){
    Map<String, String> envs = null;
    if(playbookCall.getConfig().getEnvironment() == Environment.OPENSTACK){
         envs = dataSource.getOpenstackConfig().asEnvironmentMap();
    }
    return new CommandExec(playbookCall.asCommand(),null, null, runDir ); 
}
}
