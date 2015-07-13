package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.KVMCOMConfig;

@Component("UPGRADE_KVM_HANDLER")
@Scope(value = "prototype")
public class UpgradeHandler extends DefaultHandler 
{

    private static Logger log = LoggerFactory.getLogger(UpgradeHandler.class);
    @Override
    public void onStart()
    {
    		runningComstackLock.lock(((KVMCOMConfig)config).getStackName(), Action.UPGRADE);
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade succeed");
        COMStack stack = new COMStack(config);
        service.update(stack);
        runningComstackLock.unlock(((KVMCOMConfig)config).getStackName());
        
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }
        runningComstackLock.unlock(((KVMCOMConfig)config).getStackName());

    }


}
