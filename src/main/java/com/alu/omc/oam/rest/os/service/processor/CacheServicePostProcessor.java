package com.alu.omc.oam.rest.os.service.processor;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.conf.interceptor.intf.UnLogable;
import com.alu.omc.oam.rest.os.service.CinderService;
import com.alu.omc.oam.rest.os.service.ComputeService;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@Component
public class CacheServicePostProcessor implements BeanPostProcessor, UnLogable {

	@Resource
	private YaoOsClientService yaoOsClientService;
	
	@Override
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
		if(obj instanceof ComputeService) 
		{  
            OSClient client =  yaoOsClientService.getOsClient();
			((ComputeService)obj).getInstanceList(client);
			return obj;
        }
		else if(obj instanceof CinderService)
		{
			OSClient client =  yaoOsClientService.getOsClient();
			((CinderService)obj).getAllVolumes(client);
			return obj;
		}
		else
			return obj;
        
	}

	@Override
	public Object postProcessBeforeInitialization(Object obj, String arg1)
			throws BeansException {
		return obj;
	}
}
