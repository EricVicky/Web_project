package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.RunningComstackLock;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.log.ParseResult;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;


@Component("INSTALL_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class InstallOSHandler extends DefaultHandler{

    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[deploy\\_stack\\s\\|\\scheck\\spresence\\sof\\sheat\\sstack\\].*$");
    private static Logger logger = LoggerFactory.getLogger(InstallOSHandler.class);
	@Override
	public void onStart() {
		super.onStart();
	    logger.info("start deployment on openstack");
	}

	@Override
	public void onError() {
		logger.error("deployment on OS failed");
	}

	@Override
	public void onSucceed() {
		logger.info("deployment on OS succeeded");
		
	}
	
	@Override
	public void Parse(String log) {
		if(hasError(log)){
	    	  this.succeed  = false;
	      }
		Object msg = logParser.parse(log);
		logger.info("log=" + msg);
		logger.info("channel=" + this.getFulltopic());
		if((stackPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.add(stack);
	      }
	    sender.send(getFulltopic(), msg );
		
	}
	
	private boolean hasError(String log){
    	return false;
    }
	@Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.INSTALL_SUCCEED;
		}else{
			return ActionResult.INSTALL_FAIL;
		}
	}
	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.INSTALL;
	}

}
