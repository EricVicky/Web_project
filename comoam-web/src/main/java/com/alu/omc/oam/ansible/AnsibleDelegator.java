package com.alu.omc.oam.ansible;

import javax.annotation.Resource;

import org.openstack4j.openstack.networking.domain.NeutronSubnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionKey;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig.COMProvidernetwork;
import com.alu.omc.oam.log.LogParserFactory;
import com.alu.omc.oam.rest.os.service.NeutronService;
import com.alu.omc.oam.util.NetworkUtil;

@Component
public class AnsibleDelegator implements ApplicationContextAware
{
    
    private static Logger log = LoggerFactory.getLogger(AnsibleDelegator.class);

    private ApplicationContext applicationContext;
    
    IAnsibleInvoker ansibleInvoker; 
    
    @Resource
    private LogParserFactory logParserFactory;


    public void execute(Action action, COMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, action);
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
    public void execute(Action action, OSCOMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, action);
        try
        {
           ansibleInvoker = (IAnsibleInvoker) applicationContext.getBean("ansibleInvoker");
           NeutronService neutronService =  (NeutronService)applicationContext.getBean("neutronService");
           COMProvidernetwork providerNetwork = config.getCom_provider_network();
           NeutronSubnet subnet =  neutronService.getSubetById(providerNetwork.getSubnet());
           providerNetwork.setDns1(subnet.getDnsNames()==null && subnet.getDnsNames().size() >0?subnet.getDnsNames().get(0):"8.8.8.8");
           providerNetwork.setGateway(subnet.getGateway());
           providerNetwork.setNetmask(NetworkUtil.getNetMask(subnet.getCidr()));
           ansibleInvoker.invoke(playbookCall, getHandler(action, config));
        }
        catch (AnsibleException e)
        {
            e.printStackTrace();
        }
    } 
    private IAnsibleHandler getHandler(Action action, COMConfig config)
    {
      String handlerBeanName = new ActionKey(action, config.getEnvironment(), config.getCOMType())
      								.toString().concat(IAnsibleHandler.HANDLER_END_FIX);
      IAnsibleHandler handler =   (IAnsibleHandler) applicationContext
                .getBean(handlerBeanName);
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
