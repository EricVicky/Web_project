package com.alu.omc.oam.ansible;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.input.Tailer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.service.WebsocketSender;
import com.alu.omc.oam.util.CommandExec;

@Component
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    private static final String ANSIBLE_COMMAND = "ansible-playbook ";
    @Resource
    private Ansibleworkspace ansibleworkspace;
    @Resource
    WebsocketSender websocketSender;
    public void invoke(final PlaybookCall pc) throws AnsibleException
    
    {
       final Tailer tailer = Tailer.create(this.getWorkSpace().getLogFile(), new Loglistener(websocketSender), 1000, false);
        try
        {
        	Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					String command = ANSIBLE_COMMAND.concat(pc.prepare(ansibleworkspace));
                    CommandExec commandExec = new CommandExec(command, null,
                            null, new File(ansibleworkspace.getWorkingdir()));
		            try {
						commandExec.execute();
					} catch (Exception e) {
						e.printStackTrace();
						throw new AnsibleException("failed to execute command " +command, e);
                    }
                    finally
                    {
                        tailer.stop();
                    }
				}
        		
        	});

        	thread.start();
            
        }
        catch (Exception e)
        {
           tailer.stop();
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
