package com.alu.omc.oam.ansible;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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


    public void execute(final Action action, final COMConfig config){
        final PlaybookCall playbookCall = new PlaybookCall(config, action);
        delayInvoke(action, config, playbookCall);
    }
    private void delayInvoke(final Action action, final COMConfig config,
            final PlaybookCall playbookCall)
    {
        try
        {
            ansibleInvoker = (IAnsibleInvoker) applicationContext.getBean("ansibleInvoker");
            new Thread(new Runnable(){

                @Override
                public void run()
                {
                    try
                    {
                        Thread.currentThread().sleep(1500);
                        ansibleInvoker.invoke(playbookCall, getHandler(action, config));
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    
                }
                
            }).start();
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
           if(!StringUtils.isBlank(providerNetwork.getV6_subnet())){
               NeutronSubnet v6subnet =  neutronService.getSubetById(providerNetwork.getV6_subnet());
               providerNetwork.setV6_gateway(v6subnet.getGateway());
               if(v6subnet != null){
                   providerNetwork.setV6_prefix(NetworkUtil.getNetWorkPrefix(v6subnet.getCidr()));
               }
           }
           delayInvoke(action, config, playbookCall);
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
    
    
    private void deplayInvoke(){
        
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
       this.applicationContext =  applicationContext;
        
    } 
}
