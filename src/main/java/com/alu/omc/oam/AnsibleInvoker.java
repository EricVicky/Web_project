package com.alu.omc.oam;

public class AnsibleInvoker
{
    private static final String ANSIBLE_COMMAND = "ansible-playbook ";
    public void invoke(PlaybookCall pc)
    {
        Ansibleworkspace workspace =  new Ansibleworkspace();
        String command = ANSIBLE_COMMAND.concat(pc.prepare(workspace));
    }
    
    
    
}
