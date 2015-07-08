package com.alu.omc.oam.rest.os.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.storage.block.domain.ExtAvailabilityZone;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.rest.os.domain.cinder.YaoVolume;
import com.alu.omc.oam.rest.os.service.CinderService;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@RestController
@RequestMapping("/nfv/os/cinder")
public class CinderController {

	@Resource
	private YaoOsClientService yaoOsClientService;
	@Resource
	private CinderService cinderService;

	public void setYaoOsClientService(YaoOsClientService yaoOsClientService) {
		this.yaoOsClientService = yaoOsClientService;
	}
	
	public void setCinderService(CinderService cinderService) {
		this.cinderService = cinderService;
	}

	@RequestMapping("/volume/list")
	public List<YaoVolume> getAllVolumes()
	{
		OSClient client = yaoOsClientService.getOsClient();
		if(client != null)
			return cinderService.getAllVolumes(client);
		else
			return Collections.<YaoVolume>emptyList();
	}
	
	@RequestMapping("/zone/list")
	public List<ExtAvailabilityZone> getAllZones()
	{
	    OSClient client = yaoOsClientService.getOsClient();
		if(client != null){
		    System.out.println(cinderService.getAvaliabilityZoneList(client));
			return cinderService.getAvaliabilityZoneList(client);
		}
		else
			return Collections.<ExtAvailabilityZone>emptyList();
	}
}
