package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;

@Component("BACKUP_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class BackupOSHandler extends DefaultHandler {

    private static Logger log = LoggerFactory.getLogger(BackupOSHandler.class);
    @Override
    public void onStart()
    {
    	
    }
    
    @SuppressWarnings("unchecked")
	private OSCOMConfig getOSConfig(){
    	return ((BACKUPConfig<OSCOMConfig>)config).getConfig();
    }

    @Override
    public void onSucceed()
    {
        log.info("backup succeed");
        
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }

    }
    public String getFulltopic(){
    	return this.topic.concat(getOSConfig().getStack_name());
     }
    @Override
    public void onError()
    {

    }
}


