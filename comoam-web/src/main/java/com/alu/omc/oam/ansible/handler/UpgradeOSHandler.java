package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.log.ParseResult;

@Component("UPGRADE_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class UpgradeOSHandler extends DefaultHandler 
{

    private static Logger log = LoggerFactory.getLogger(UpgradeOSHandler.class);
    @Override
    public void onStart()
    {
    		//runningComstackLock.lock(((OSCOMConfig)config).getStackName(), Action.UPGRADE);
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade succeed");
        COMStack stack = new COMStack(config);
        service.update(stack);
        //runningComstackLock.unlock(((OSCOMConfig)config).getStackName());
        
    }

    @Override
    public void onEnd()
    {
    	if(this.succeed){
        	this.onSucceed();
        	END.setResult(ParseResult.SUCCEED);
        }else{
        	END.setResult(ParseResult.FAILED);
            this.onError();
        }
        sender.send(getFulltopic(), END);
        //runningComstackLock.unlock(((OSCOMConfig)config).getStackName());

    }
	public String getFulltopic(){
	       OSCOMConfig cfg = (OSCOMConfig)config;
	       return this.topic.concat(cfg.getStack_name());
	    }
	@Override
	public void setConfig(COMConfig config) {
		this.config = config;
		
	}
	
    public COMConfig getConfig()
    {
        return config;
    }

}
