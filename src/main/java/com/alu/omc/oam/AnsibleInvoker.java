package com.alu.omc.oam;

import java.io.File;
import java.io.IOException;

import com.alu.omc.oam.util.CommandExec;
import com.alu.omc.oam.util.CommandResult;

public class AnsibleInvoker
{
    private static final String ANSIBLE_COMMAND = "ansible-playbook ";
    public void invoke(PlaybookCall pc) throws IOException, InterruptedException
    {
        Ansibleworkspace workspace =  new Ansibleworkspace();
        String command = ANSIBLE_COMMAND.concat(pc.prepare(workspace));
        CommandExec commandExec = new CommandExec(command, null, null, new File(workspace.getWorkingdir()));
        commandExec.execute();
    }
    
    
    
}
