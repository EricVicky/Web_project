package com.alu.omc.oam.ansible.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMStack;


@Component("UPGRADE_KVM_HANDLER")
@Scope(value = "prototype")
public class UpgradeHandler extends DefaultHandler 
{
    private static Logger log = LoggerFactory.getLogger(UpgradeHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("upgrade start on KVM");
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade on KVM succeed");
        COMStack stack = new COMStack(config);
        service.update(stack);
        
    }
    @Override
    public void onError()
    {
        log.error("upgrade on KVM failed");
    }
    
    
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.UPGRADE_SUCCEED;
		}else{
			return ActionResult.UPGRADE_FAIL;
		}
	}

	@Override
	public Action getAction() {
		return Action.UPGRADE;
	}
}
