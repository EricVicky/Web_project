package com.alu.omc.oam.ansible;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.log.LogParserFactory;

@Component
public class AnsibleDelegator implements ApplicationContextAware
{
    
    private static Logger log = LoggerFactory.getLogger(AnsibleDelegator.class);

    private ApplicationContext applicationContext;
    
    IAnsibleInvoker ansibleInvoker; 
    
    @Resource
    private LogParserFactory logParserFactory;
    




    public void execute(Action action, COMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, Action.INSTALL);
        try
        {
            ansibleInvoker = (IAnsibleInvoker) applicationContext.getBean("ansibleInvoker");
            ansibleInvoker.invoke(playbookCall, getHandler(action, config));
        }
        catch (AnsibleException e)
        {
            e.printStackTrace();
        }
       
    } 
    
    private IAnsibleHandler getHandler(Action action, COMConfig config)
    {
      IAnsibleHandler handler =   (IAnsibleHandler) applicationContext
                .getBean("defaulthandler");
      handler.setConfig(config);
      handler.setLogParser(logParserFactory.getLogParser(action, config));
      return handler;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
       this.applicationContext =  applicationContext;
        
    } 
}
