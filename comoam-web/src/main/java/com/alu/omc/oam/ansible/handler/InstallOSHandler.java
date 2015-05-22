package com.alu.omc.oam.ansible.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[deploy\\_stack\\s\\|\\scheck\\spresence\\sof\\sheat\\sstack\\].*$");
    private static Logger logger = LoggerFactory.getLogger(InstallOSHandler.class);
	@Override
	public void onStart() {
	    logger.info("start deployment on openstack");
	}

	@Override
	public void onError() {
		logger.error("deployent on OS failed");
	}

	@Override
	public void onSucceed() {
		logger.info("deployment on OS succeed");
		
	}
	
	@Override
	public void onEnd() {
		if(this.succeed){
        	this.onSucceed();
        }else{
            this.onError();
        }
		logger.info("deployment on OS completed");
		sender.send(getFulltopic(), END);
		
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
	
	public String getFulltopic(){
	       OSCOMConfig cfg = (OSCOMConfig)config;
	       return this.topic.concat(cfg.getStack_name());
	    }

	@Override
	public void setConfig(COMConfig config) {
		this.config = config;
		
	}
	
    public COMConfig getConfig()
    {
        return config;
    }

	@Override
	public void setLogParser(ILogParser logParser) {
		this.logParser = logParser;
		
	}
	
    @Override
    public void onProcessComplete(int paramInt)
    {
       logger.info("the installaton on openstack complete");
       this.onEnd(); 
        
    }

    @Override
    public void onProcessFailed(ExecuteException paramExecuteException)
    {
        logger.error("failed to run installation on openstack", paramExecuteException);
        this.succeed = false;
        this.onEnd();
        
    }

}
