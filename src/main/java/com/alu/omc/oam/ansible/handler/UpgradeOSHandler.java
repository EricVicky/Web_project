package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OSCOMConfig;

@Component("UPGRADE_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class UpgradeOSHandler extends DefaultHandler 
{

    private static Logger log = LoggerFactory.getLogger(UpgradeOSHandler.class);
    @Override
    public void onStart()
    {
    		//runningContext.lock(((OSCOMConfig)config).getStackName(), Action.UPGRADE);
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade succeed");
        COMStack stack = new COMStack(config);
        service.update(stack);
        //runningContext.unlock(((OSCOMConfig)config).getStackName());
        
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }
        //runningContext.unlock(((OSCOMConfig)config).getStackName());

    }


}
