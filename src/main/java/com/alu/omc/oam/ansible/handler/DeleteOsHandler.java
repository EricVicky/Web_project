package com.alu.omc.oam.ansible.handler;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
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

@Component("DELETE_OS_HANDLER")
@Scope(value = "prototype")

public class DeleteOsHandler implements IAnsibleHandler{
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
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
    private static Logger log = LoggerFactory.getLogger(DeleteOsHandler.class);	
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
        service.delete(config.getStackName());
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

	public String getFulltopic(){
	       KVMCOMConfig cfg = (KVMCOMConfig)config;
	       return this.topic.concat(cfg.getHost().getIp_address());
	}
	
	@Override
	public void setLogParser(ILogParser logParser) {
		this.logParser = logParser;
		
	}
    @Override
    public void onProcessComplete(int paramInt)
    {
       this.onEnd(); 
        
    }

    @Override
    public void onProcessFailed(ExecuteException paramExecuteException)
    {
        this.succeed = false;
        this.onEnd();
        
    }

    public void setConfig(COMConfig config)
    {
        this.config = config;
    }

}

