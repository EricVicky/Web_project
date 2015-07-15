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
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.log.ParseResult;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;

@Component("INSTALL_KVM_HANDLER")
@Scope(value = "prototype")
public class DefaultHandler implements IAnsibleHandler 
{
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
    private Pattern stackPattern = Pattern.compile("^.*TASK\\:\\s\\[vnf\\_create\\_vms\\s\\|\\screate\\svirtual\\smachine\\sinstance\\].*$");
    private static Logger log = LoggerFactory.getLogger(DefaultHandler.class);
    @Override
    public void onStart()
    {
    	log.info("deployment on KVM start");
        runningComstackLock.lock(((KVMCOMConfig)config).getStackName(), Action.INSTALL);
        log.info(((KVMCOMConfig)config).getStackName() + " is locked");
    }

    @Override
    public void onError()
    {
        log.error("deployent on KVM failed");
        runningComstackLock.unlock(((KVMCOMConfig)config).getStackName());
    }

    @Override
    public void onSucceed()
    {
        log.info("deployment on KVM succeed");
        runningComstackLock.unlock(((KVMCOMConfig)config).getStackName());
    }

    @Override
    public void onEnd()
    {
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
    public void Parse(String log)
    {
      if(hasError(log)){
    	  this.succeed  = false;
      }
      if((stackPattern.matcher(log)).find()){
    	  COMStack stack = new COMStack(config);
          service.add(stack);
      }
      sender.send(getFulltopic(), logParser.parse(log));
    }
    
    private boolean hasError(String log){
    	return false;
    }

    public COMConfig getConfig()
    {
        return config;
    }

    public void setConfig(COMConfig config)
    {
        this.config = config;
    }
    
    public String getFulltopic(){
       KVMCOMConfig cfg = (KVMCOMConfig)config;
       return this.topic.concat(cfg.getStackName());
    }
    
    @Override
    public void setLogParser(ILogParser logParser){
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

}
