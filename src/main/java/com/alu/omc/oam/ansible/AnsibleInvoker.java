package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.util.CommandExec;

@Component
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    private static final String ANSIBLE_COMMAND = "ansible-playbook ";
    @Resource
    private Ansibleworkspace ansibleworkspace;
    public void invoke(PlaybookCall pc) throws IOException, InterruptedException
    {
        String command = ANSIBLE_COMMAND.concat(pc.prepare(ansibleworkspace));
        CommandExec commandExec = new CommandExec(command, null, null, new File(ansibleworkspace.getWorkingdir()));
        commandExec.execute();
    }
    
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        // TODO Auto-generated method stub
        return ansibleworkspace;
    }
    
    
    
    
}
