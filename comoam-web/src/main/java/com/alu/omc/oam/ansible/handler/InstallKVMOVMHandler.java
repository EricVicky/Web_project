package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.RunningHostLock;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OVMCOMConfig;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;

@Component("INSTALL_KVM_OVM_HANDLER")
@Scope(value = "prototype")
public class InstallKVMOVMHandler implements IAnsibleHandler {

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
    private Pattern stackPattern = Pattern.compile("^.*TASK:\\s\\[wait\\_for\\_server\\_start\\s\\|\\swait\\sfor\\sguest\\sos\\sto\\sstart\\].*$");
    private static Logger log = LoggerFactory.getLogger(DefaultHandler.class);
    
	@Override
	public void onProcessComplete(int exitValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProcessFailed(ExecuteException e) {

	}

	@Override
	public void onStart() {
    	log.info("deployment on KVM OVM start");
//        runningContext.lock(((OVMCOMConfig)config).getHost(), Action.INSTALL);
	}

	@Override
	public void onError() {
    	log.info("deployment on KVM OVM failed");
//        runningContext.unlock(((OVMCOMConfig)config).getHost());
	}

	@Override
	public void onSucceed() {
    	log.info("deployment on KVM OVM succeed");
//        runningContext.unlock(((OVMCOMConfig)config).getHost());
	}

	public String getFulltopic(){
	       OVMCOMConfig cfg = (OVMCOMConfig)config;
	       return this.topic.concat(cfg.getHost().getIp_address());
	    }
	
	@Override
	public void onEnd() {
		if(this.succeed){
        	this.onSucceed();
        }else{
            this.onError();
        }
        log.info("deployment on KVM completed");
        sender.send(getFulltopic(), END);
	}

	@Override
	public void Parse(String log) {

	      if((stackPattern.matcher(log)).find()){
	    	  COMStack stack = new COMStack(config);
	          service.add(stack);
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
