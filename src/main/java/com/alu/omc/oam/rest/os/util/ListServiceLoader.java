package com.alu.omc.oam.rest.os.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openstack4j.api.OSClient;
import org.openstack4j.common.RestService;

import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@SuppressWarnings("rawtypes")
public class ListServiceLoader implements Callable<List> {

	private Object restService;
	private YaoOsClientService clientService;
	
	public ListServiceLoader(Object restService, YaoOsClientService clientService)
	{
		this.restService = restService;
		this.clientService = clientService;
	}

	public void setRestService(RestService restService) {
		this.restService = restService;
	}

	public void setClientService(YaoOsClientService clientService) {
		this.clientService = clientService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List call()
	{
		
		OSClient client = clientService.getOsClient();
		
		Class clz = restService.getClass();
		Map<String, String> params = new HashMap<String, String>();
		params.put("limit", Integer.MAX_VALUE+"");
		
		try {
			Method listMethodWithArgs = clz.getMethod("list", Map.class);
			if(listMethodWithArgs != null)
			{	
				Object rtnObj = listMethodWithArgs.invoke(restService, params);
				return (List)rtnObj;
			}
		} catch (Exception e) {
			try {
				Method listMethod = clz.getMethod("list",null);
				
				if(listMethod != null)
				{	
					Object rtnObj = listMethod.invoke(restService);
					return (List)rtnObj;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			} 
		} 
		
		return null;
		
	}

}
