package com.alu.omc.oam.ansible;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.input.Tailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;
import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.util.CommandProtype;
import com.alu.omc.oam.util.CommandResult;
import com.alu.omc.oam.util.ICommandExec;

@Component("ansibleInvoker")
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    @Resource
    private Ansibleworkspace ansibleworkspace;

    @Resource
    private  CommandProtype commandProtype;


    private static Logger log = LoggerFactory.getLogger(AnsibleInvoker.class);

    public void invoke(final PlaybookCall pc, final IAnsibleHandler handler) throws AnsibleException
    
    {
        final Tailer tailer = Tailer.create(this.getWorkSpace().getLogFile(),
                new Loglistener(handler), 1000, false);
        log.info("create tailer"); 
       try
        {
        	Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					String command = pc.prepare(ansibleworkspace);
				    ICommandExec	commandExe = commandProtype.create(command, new File(ansibleworkspace.getWorkingdir()));
		            try {

						CommandResult rst = commandExe.execute();
						if(rst.isFailed()){
						    handler.onError();
						}else{
						    handler.onEnd();
						}
					} catch (Exception e) {
					    handler.onError();
						log.error("failed to call ansible" , e);
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
     	    handler.onError();
           tailer.stop();
           log.error("failed to call ansible" , e);
           throw new AnsibleException("failed to call ansible", e); 
        }
    }
    
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        return ansibleworkspace;
    }



    
    
}
