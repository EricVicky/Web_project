package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.util.CommandExec;

@Component
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    private static final String ANSIBLE_COMMAND = "ansible-playbook ";
    @Resource
    private Ansibleworkspace ansibleworkspace;
    public void invoke(final PlaybookCall pc) throws AnsibleException
    {
        try
        {
        	Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					String command = ANSIBLE_COMMAND.concat(pc.prepare(ansibleworkspace));
		            CommandExec commandExec = new CommandExec(command, null, null, new File(ansibleworkspace.getWorkingdir()));
		            try {
						commandExec.execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
        		
        	});
        	
        	thread.start();
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
           throw new AnsibleException("failed to call ansible", e); 
        }
    }
    
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        // TODO Auto-generated method stub
        return ansibleworkspace;
    }
    
    
    
    
}
