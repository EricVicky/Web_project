package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMStack;

@Component("UPGRADE_KVM_QOSAC_HANDLER")
@Scope(value = "prototype")
public class UpgradeKVMQOSACHandler extends DefaultHandler{

    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[wait\\_for\\_server\\_start\\s\\|\\swait\\sfor\\sguest\\sos\\sto\\sstart\\].*$");
    private static Logger log = LoggerFactory.getLogger(UpgradeKVMQOSACHandler.class);

	@Override
	public void onStart() {
		super.onStart();
    	log.info("upgrade QOSAC on KVM start");
        
	}

	@Override
	public void onError() {
    	log.info("upgrade QOSAC on KVM failed");
    }

	@Override
	public void onSucceed() {
    	log.info("upgrade QOSAC on KVM succeeded");
        COMStack stack = new COMStack(config);
        service.update(stack);
  	}

	@Override
	public void Parse(String log) {

	      if((stackPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.update(stack);
	      }
	      sender.send(getFulltopic(), logParser.parse(log));

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
