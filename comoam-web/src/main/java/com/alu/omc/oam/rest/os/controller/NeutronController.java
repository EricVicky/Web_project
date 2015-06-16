package com.alu.omc.oam.rest.os.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.rest.os.domain.neutron.YaoNetwork;
import com.alu.omc.oam.rest.os.domain.neutron.YaoPort;
import com.alu.omc.oam.rest.os.service.NeutronService;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;
//import ems.yao.restful.os.service.Port;



@RestController
@RequestMapping("/nfv/os/neutron")
public class NeutronController {

	@Resource
	private YaoOsClientService yaoOsClientService;
	@Resource
	private NeutronService neutronService;

	public void setYaoClientService(YaoOsClientService yaoOsClientService) {
		this.yaoOsClientService = yaoOsClientService;
	}
	public void setNeutronService(NeutronService neutronService) {
		this.neutronService = neutronService;
	}
	
	@RequestMapping("/network/list")
	public List<YaoNetwork> getNetworkList()
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getNetworkList(os);
		else
			return Collections.<YaoNetwork>emptyList();
	}
	
	@RequestMapping("/{networkId}/subnet/list/names")
	public List<NeutronService.IdNamePair> getSubnetNamesByNetworkId(@PathVariable String networkId)
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getSubetNamesByNetworkId(os, networkId);
		else
			return Collections.<NeutronService.IdNamePair>emptyList();
	}
	@RequestMapping("/{networkId}/subnet/v4/list/names")
	public List<NeutronService.IdNamePair> getV4SubnetNamesByNetworkId(@PathVariable String networkId)
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getSubetNamesByNetworkId(os, networkId, true);
		else
			return Collections.<NeutronService.IdNamePair>emptyList();
	}
	@RequestMapping("/{networkId}/subnet/v6/list/names")
	public List<NeutronService.IdNamePair> getV6SubnetNamesByNetworkId(@PathVariable String networkId)
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getSubetNamesByNetworkId(os, networkId, false);
		else
			return Collections.<NeutronService.IdNamePair>emptyList();
	}
	@RequestMapping("/subnet/list/names")
	public List<NeutronService.IdNamePair> getSubnetNames()
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getSubetNames(os);
		else
			return Collections.<NeutronService.IdNamePair>emptyList();
	}
	
	@RequestMapping("/network/list/names")
	public List<NeutronService.IdNamePair> getNetworkNames()
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return neutronService.getNetworkNames(os);
		else
			return Collections.<NeutronService.IdNamePair>emptyList();
	}
	
	@RequestMapping("/network/{networkid}/port/list")
	public List<YaoPort> getPortList(@PathVariable String networkid)
	{
       	
        OSClient os = yaoOsClientService.getOsClient();
       	if(os != null)
			return neutronService.getPortList(os,networkid);
		else
			return Collections.<YaoPort>emptyList();
	}
	
}
