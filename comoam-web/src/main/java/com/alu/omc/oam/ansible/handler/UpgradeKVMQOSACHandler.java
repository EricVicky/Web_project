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
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OVMCOMConfig;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.log.ParseResult;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;

@Component("UPGRADE_KVM_QOSAC_HANDLER")
@Scope(value = "prototype")
public class UpgradeKVMQOSACHandler implements IAnsibleHandler{
	@Resource
    COMStackService service;
    @Resource
    WebsocketSender sender;
    @Resource
    RunningComstackLock runningComstackLock;
    String topic = "/log/tail/";
    COMConfig config;
    ILogParser logParser;
    Boolean succeed = true;
    ParseResult END = new ParseResult();
    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[wait\\_for\\_server\\_start\\s\\|\\swait\\sfor\\sguest\\sos\\sto\\sstart\\].*$");
    private static Logger log = LoggerFactory.getLogger(DefaultHandler.class);
    
	@Override
	public void onProcessComplete(int exitValue) {
		this.onEnd();
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		this.onEnd();
	}

	@Override
	public void onStart() {
    	log.info("deployment on KVM OVM start");
        runningComstackLock.lock(((OVMCOMConfig)config).getStackName(), Action.INSTALL);
	}

	@Override
	public void onError() {
    	log.info("deployment on KVM OVM failed");
        runningComstackLock.unlock(((OVMCOMConfig)config).getStackName());
	}

	@Override
	public void onSucceed() {
    	log.info("upgrade succeed");
        COMStack stack = new COMStack(config);
        service.update(stack);
        runningComstackLock.unlock(((OVMCOMConfig)config).getStackName());
	}

	public String getFulltopic(){
	       OVMCOMConfig cfg = (OVMCOMConfig)config;
	       return this.topic.concat(cfg.getStackName());
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

	      if((stackPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.update(stack);
	      }
	      sender.send(getFulltopic(), logParser.parse(log));

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
