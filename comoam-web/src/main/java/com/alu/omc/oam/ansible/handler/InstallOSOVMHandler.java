package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMStack;



public abstract class InstallOSOVMHandler extends DefaultHandler {

    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[deploy\\_stack\\s\\|\\scheck\\spresence\\sof\\sheat\\sstack\\].*$");
    private static Logger log = LoggerFactory.getLogger(InstallOSOVMHandler.class);

	@Override
	public void onStart() {
		super.onStart();
    	log.info("OVM deployment on Openstack start");       
	}

	@Override
	public void onError() {
    	log.error("OVM deployment on Openstack failed");
	}

	@Override
	public void onSucceed() {
    	log.info("OVM deployment on Openstack succeeded");
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
