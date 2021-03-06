package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.ansible.RunningComstackLock;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OVMCOMConfig;


public abstract class InstallKVMOVMHandler extends DefaultHandler {

    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[wait\\_for\\_server\\_start\\s\\|\\swait\\sfor\\sguest\\sos\\sto\\sstart\\].*$");
    private static Logger log = LoggerFactory.getLogger(InstallKVMOVMHandler.class);    

	@Override
	public void onStart() {
		super.onStart();
    	log.info("deployment on KVM OVM start");       
	}

	@Override
	public void onError() {
    	log.error("deployment on KVM OVM failed");
	}

	@Override
	public void onSucceed() {
    	log.info("deployment on KVM OVM succeed");
	}

	@Override
	public void Parse(String log) {

	      if((stackPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.add(stack);
	      }
	      sender.send(getFulltopic(), logParser.parse(log));

	}
	
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.INSTALL_SUCCEED;
		}else{
			return ActionResult.INSTALL_FAIL;
		}
	}
    public Action getAction(){
    	return Action.INSTALL;
    }

}
