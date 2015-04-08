package com.alu.omc.oam.util;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;

@Component
public class CommandProtype
{
public ICommandExec create(String command, File workingdir){
    if(SystemUtils.IS_OS_WINDOWS){
        return new MockCommandExec(command, null,
                            null, workingdir);
    }else{
        return new CommandExec(command, null,
                            null, workingdir);
    }
}
}
