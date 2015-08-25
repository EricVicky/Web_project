package com.alu.omc.oam.ansible.handler;

import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.LogStatus;
import com.alu.omc.oam.log.ParseResult;

@Component("DELETE_KVM_HANDLER")
@Scope(value = "prototype")

public class DeleteKVMHandler extends DefaultHandler{
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID     = -3535916139459672300L; 
    private static Logger log = LoggerFactory.getLogger(DeleteKVMHandler.class);	
	@Override
	
	public void onStart() {
	  log.error("start to delete" + config.getStackName());	
	}

	@Override
	public void onError() {
	  log.error("delete " + config.getStackName() + " failed!");	
		
	}

	@Override
	public void onSucceed() {
	    runningComstackLock.unlock(config.getStackName());
        service.delete(config.getStackName());
	}

	@Override
	public void onEnd() {
		if(this.succeed){
        	this.onSucceed();
        	END.setResult(ParseResult.SUCCEED);
        }else{
        	END.setResult(ParseResult.FAILED);
            this.onError();
        }
        sender.send(getFulltopic(), END);
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

    @Override
    public Action getAction()
    {
        return Action.DELETE;
    }

}
