package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMStack;

@Component("UPGRADE_OPENSTACK_QOSAC_HANDLER")
@Scope(value = "prototype")
public class UpgradeOSQOSACHandler extends DefaultHandler {

    private static Pattern updatedPattern = Pattern.compile("wait\\sfor\\svirtual\\smachines\\sto\\sbe\\salive");
    private static Logger logger = LoggerFactory.getLogger(UpgradeOSQOSACHandler.class);
	@Override
	public void onStart() {
		super.onStart();
	    logger.info("start upgrade QOSAC on openstack");
	}

	@Override
	public void onError() {
		logger.error("upgrade QOSAC on OS failed");
	}

	@Override
	public void onSucceed() {
		logger.info("upgrade QOSAC on OS succeeded");
	    COMStack stack = new COMStack(config);
	    service.update(stack);
	}

	@Override
	public void Parse(String log) {
		if(hasError(log)){
	    	  this.succeed  = false;
	      }
		Object msg = logParser.parse(log);
		if((updatedPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.update(stack);
	      }
	    sender.send(getFulltopic(), msg );
	}
	
	
	private boolean hasError(String log){
    	return false;
    }
	

    @Override
	public Action getAction() {
		return Action.UPGRADE;
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
