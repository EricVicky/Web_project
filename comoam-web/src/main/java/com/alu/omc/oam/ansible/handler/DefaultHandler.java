package com.alu.omc.oam.ansible.handler;

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
import com.alu.omc.oam.config.LogStatus;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.log.ParseResult;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.OperationLogService;
import com.alu.omc.oam.service.WebsocketSender;


@Component("DEFAULT_HANDLER")
@Scope(value = "prototype")
public abstract class DefaultHandler  implements IAnsibleHandler{
	

    Boolean succeed = true;
    ParseResult END = new ParseResult();
    COMConfig config;
    ILogParser logParser;
    @Resource
    COMStackService service;
    @Resource
    OperationLogService operationLogService;
    @Resource
    WebsocketSender sender;   
    @Resource
    RunningComstackLock runningComstackLock;
    String topic = "/log/tail/";
    private static Logger log = LoggerFactory.getLogger(DefaultHandler.class);
	public Boolean getSucceed() {
		return succeed;
	}

	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}
	@Override
	public void onStart(){
		runningComstackLock.lock(config.getStackName(), this.getAction());
        log.info(config.getStackName() + " is locked");
	}
	
	public abstract Action getAction();
    	

	@Override
    public abstract void onError();
    	

    @Override
    public abstract void onSucceed();
    	
    

    public  ActionResult getActionResult(){
    	return ActionResult.NULL;
    }

    @Override
    public void onEnd(){
    	    
    	runningComstackLock.unlock(config.getStackName());
    	if(getSucceed()){
        	onSucceed();
        	END.setResult(ParseResult.SUCCEED);
        	operationLogService.setLogStatus(config.getStackName(), LogStatus.SUCCEED);
        }else{
        	END.setResult(ParseResult.FAILED);
        	operationLogService.setLogStatus(config.getStackName(), LogStatus.FAILED);
            onError();
        } 
    	COMStack stack=service.get(config.getStackName());
    	if (stack!=null && getActionResult() != ActionResult.NULL){
        	stack.setActionResult(getActionResult());
        	service.update(stack);    		
    	}
    	sender.send(getFulltopic(), END);
    }
    @Override
    public void Parse(String log)
    {
    	
    }
    public COMConfig getConfig()
    {
        return config;
    }

    public void setConfig(COMConfig config){
    	this.config = config;
    }
    @Override
    public void setLogParser(ILogParser logParser){
    	this.logParser = logParser;
    }
	@Override
	public void onProcessComplete(int exitValue) {
		this.onEnd();
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		this.succeed = false;
		this.onEnd();
	}
	
	public String getFulltopic() {
		return this.topic.concat(config.getStackName());
	}
}
