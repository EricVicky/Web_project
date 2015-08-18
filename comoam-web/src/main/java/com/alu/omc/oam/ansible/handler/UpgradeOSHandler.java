package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
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
    	super.onStart();
    	log.info("upgrade start on openstack");
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade succeeded on openstack");
        COMStack stack = new COMStack(config);
        service.update(stack);              
    }

	@Override
	public Action getAction() {

		return Action.UPGRADE;
	}

	@Override
	public void onError() {

		log.error("upgrade failed on openstack");
	}
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.UPGRADE_SUCCEED;
		}else{
			return ActionResult.UPGRADE_FAIL;
		}
	}
	


}
