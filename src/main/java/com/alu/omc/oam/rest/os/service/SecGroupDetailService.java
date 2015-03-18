package com.alu.omc.oam.rest.os.service;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.network.SecurityGroup;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@Service
public class SecGroupDetailService {
	@Resource
	private YaoOsClientService yaoOsClientService;
	
	public SecurityGroup getSecGroupDetail(String secGroupId){
		OSClient client = yaoOsClientService.getOsClient();
		return client.networking().securitygroup().get(secGroupId);
	}
}
