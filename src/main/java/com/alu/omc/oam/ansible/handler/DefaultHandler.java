package com.alu.omc.oam.ansible.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.log.ILogParser;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.WebsocketSender;

@Component("defaulthandler")
@Scope(value = "prototype")
public class DefaultHandler implements IAnsibleHandler
{
    @Resource
    COMStackService service;
    @Resource
    WebsocketSender sender;
    String topic = "/log/tail";
    COMConfig config;
    ILogParser logParser;
    Boolean succeed = true;
    final String END = "end";
    private static Logger log = LoggerFactory.getLogger(DefaultHandler.class);
    @Override
    public void onStart()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError()
    {
        COMStack stack = new COMStack(config);
        service.add(stack);
    }

    @Override
    public void onSucceed()
    {
        COMStack stack = new COMStack(config);
        service.add(stack);
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(topic, END);
        }

    }

    @Override
    public void Parse(String log)
    {
      if(hasError(log)){
    	  this.succeed  = false;
      }
      sender.send(topic, logParser.parse(log));  
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
    
    @Override
    public void setLogParser(ILogParser logParser){
        this.logParser = logParser;
    }

}
