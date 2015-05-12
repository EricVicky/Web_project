package com.alu.omc.oam.ansible.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.RunningHostLock;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;


@Component("INSTALL_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class InstallOSHandler implements IAnsibleHandler{
	@Resource
    COMStackService service;
    @Resource
    WebsocketSender sender;
    @Resource
    RunningHostLock runningContext;
    String topic = "/log/tail/";
    COMConfig config;
    ILogParser logParser;
    Boolean succeed = true;
    final String END = "end";
    private static Logger log = LoggerFactory.getLogger(InstallOSHandler.class);
	@Override
	public void onStart() {
	//	runningContext.lock(((OSCOMConfig)config).getStack_name(), Action.INSTALL);
	}

	@Override
	public void onError() {
		COMStack stack = new COMStack(config);
        service.add(stack);
      //  runningContext.unlock(((OSCOMConfig)config).getStack_name());
	}

	@Override
	public void onSucceed() {
		COMStack stack = new COMStack(config);
        service.add(stack);
		
	}

	@Override
	public void onEnd() {
		if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }
       // runningContext.unlock(((OSCOMConfig)config).getStack_name());
		
	}

	@Override
	public void Parse(String log) {
		if(hasError(log)){
	    	  this.succeed  = false;
	      }
	      sender.send(getFulltopic(), logParser.parse(log));
		
	}
	
	private boolean hasError(String log){
    	return false;
    }
	
	public String getFulltopic(){
	       OSCOMConfig cfg = (OSCOMConfig)config;
	       return this.topic.concat(cfg.getStack_name());
	    }

	@Override
	public void setConfig(COMConfig config) {
		this.config = config;
		
	}

	@Override
	public void setLogParser(ILogParser logParser) {
		this.logParser = logParser;
		
	}

}
