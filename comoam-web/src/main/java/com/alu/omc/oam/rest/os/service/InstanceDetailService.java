package com.alu.omc.oam.rest.os.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.storage.block.Volume;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.compute.YaoFlavor;
import com.alu.omc.oam.rest.os.domain.compute.YaoInstance;
import com.alu.omc.oam.rest.os.domain.instance.detail.YaoInstanceDetail;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@Service
public class InstanceDetailService {
	@Resource
	private YaoOsClientService yaoOsClientService;
	
	public YaoInstanceDetail getInstanceDetail(String instanceId){
		OSClient client = yaoOsClientService.getOsClient();
		YaoInstanceDetail instanceDetail = new YaoInstanceDetail();
		//instance
		Server server = getInstance(client, instanceId);
		instanceDetail.setInstance(new YaoInstance(server));
		//IP
		instanceDetail.setAddresses(server.getAddresses().getAddresses());
		//flavor
		Flavor flavor = getFlavor(client, server.getFlavorId());
		instanceDetail.setFlavor(new YaoFlavor(flavor));
		//securityGroup
//		instanceDetail.setSecurityGroups(getSecurityGroup(client, instanceId));
		//volume
		instanceDetail.setVolumes(getVolumes(client, server.getOsExtendedVolumesAttached()));
		//metaData
		instanceDetail.setMetaData(getMetaData(client, instanceId));
		
		return instanceDetail;
		
	}
	
	public Server getInstance(OSClient client, String instanceId){
		Server server = client.compute().servers().get(instanceId);
        if (server != null)
            return server;
        else
            return null;
	}
	
	public Flavor getFlavor(OSClient client, String flavorId){
		Flavor flavor = client.compute().flavors().get(flavorId);
		return flavor;
	}
	
//	public List<SecurityGroup> getSecurityGroup(OSClient client, String instanceId){
//		@SuppressWarnings("unchecked")
		//TODO the listServerGroups return null.
//		List<SecurityGroup> securityGroups1 = (List<SecurityGroup>)client.compute().securityGroups().list();
//		System.out.println(securityGroups1.size());
//		List<SecurityGroup> securityGroups = (List<SecurityGroup>)client.compute().securityGroups().listServerGroups(instanceId);
//		return securityGroups;
//	}
	
	public List<Volume> getVolumes(OSClient client, List<String> volumeIds){
		List<Volume> volumes = new ArrayList<Volume>();
		for(String volumeId : volumeIds){
			volumes.add(client.blockStorage().volumes().get(volumeId));
		}
		return volumes;
	}
	
	public Map<String, String> getMetaData(OSClient client, String instanceId){
		return client.compute().servers().getMetadata(instanceId);
	}

	
	
	public YaoOsClientService getYaoOsClientService() {
		return yaoOsClientService;
	}

	public void setYaoOsClientService(YaoOsClientService yaoOsClientService) {
		this.yaoOsClientService = yaoOsClientService;
	}
}
