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
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.Environment;
import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.util.CommandProtype;
import com.alu.omc.oam.util.ICommandExec;

@Component("ansibleInvoker")
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    @Resource
    private Ansibleworkspace ansibleworkspace;

    @Resource
    private  CommandProtype commandProtype;
    
    @Resource
    private JsonDataSource dataSource;


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
					log.info(command);
                    ICommandExec commandExe = null;
                    File workingDir = new File(ansibleworkspace.getRunDir());
					if( pc.getConfig().getEnvironment() == Environment.OPENSTACK){
                        commandExe = commandProtype.create(command, workingDir, dataSource.getOpenstackConfig().asEnvironmentMap());
                    }else{
                        commandExe = commandProtype.create( command, workingDir, null);
                    }
		            try {
		                handler.onStart();
						commandExe.execute(handler);
					} catch (Exception e) {
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
