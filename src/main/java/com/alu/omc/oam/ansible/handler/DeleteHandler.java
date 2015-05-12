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
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;

@Component("DELETE_KVM_HANDLER")
@Scope(value = "prototype")

public class DeleteHandler implements IAnsibleHandler{

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
    private static Logger log = LoggerFactory.getLogger(DeleteHandler.class);	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		
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
	}

	@Override
	public void Parse(String log) {
		if(hasError(log)){
	    	  this.succeed  = false;
	      }
	      sender.send(getFulltopic(), logParser.parse(log)); 
		
	}
	
	public COMConfig getConfig()
    {
        return config;
    }
	
	private boolean hasError(String log){
    	return false;
    }

	@Override
	public void setConfig(COMConfig config) {
		this.config = config;
		
	}

	public String getFulltopic(){
	       KVMCOMConfig cfg = (KVMCOMConfig)config;
	       return this.topic.concat(cfg.getHost().getIp_address());
	}
	
	@Override
	public void setLogParser(ILogParser logParser) {
		this.logParser = logParser;
		
	}

}
