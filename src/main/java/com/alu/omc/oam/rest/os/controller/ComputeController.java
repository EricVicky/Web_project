package com.alu.omc.oam.rest.os.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.compute.domain.NovaKeypair;
import org.openstack4j.openstack.compute.domain.ext.ExtAvailabilityZone;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.rest.os.domain.compute.YaoFlavor;
import com.alu.omc.oam.rest.os.domain.compute.YaoInstance;
import com.alu.omc.oam.rest.os.domain.compute.YaoLimits;
import com.alu.omc.oam.rest.os.domain.compute.YaoSecurityGroup;
import com.alu.omc.oam.rest.os.service.ComputeService;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@RestController
@RequestMapping("/nfv/os/compute")
public class ComputeController {

    @Resource
	private YaoOsClientService yaoClientService;   
    @Resource
    private ComputeService computeService;  
 
    public void setComputeService(ComputeService computeService) {
		this.computeService = computeService;
	}

	public void setYaoClientService(YaoOsClientService yaoClientService) {
		this.yaoClientService = yaoClientService;
	}
	
	@RequestMapping("/availabilityzone/list")
	public List<ExtAvailabilityZone> getAvaliabilityZoneList()
	{
		OSClient os = yaoClientService.getOsClient();
        if(os != null)
        	return computeService.getAvaliabilityZoneList(os);
        else
        	return Collections.<ExtAvailabilityZone>emptyList();
	}
	
	@RequestMapping("/availabilityzone/list/names")
	public List<String> getAvaliabilityZoneNames()
	{
		OSClient os = yaoClientService.getOsClient();
        if(os != null)
        	return computeService.getAvaliabilityZoneNames(os);
        else
        	return Collections.<String>emptyList();
	}

	@RequestMapping("/instances")
    public List<YaoInstance> getInstanceList() {
        OSClient os = yaoClientService.getOsClient();
        if(os != null)
        	return computeService.getInstanceList(os);
        else
        	return Collections.<YaoInstance>emptyList();
    }
    
    @RequestMapping("/keypair/list")
    public List<NovaKeypair> getKeyPairList()
    {
    	OSClient os = yaoClientService.getOsClient();
    	if(os != null)
    		return computeService.getKeyPairList(os);
    	else
    		return Collections.<NovaKeypair>emptyList();
    }
    
    @RequestMapping("/keypair/list/names")
    public List<String> getKeyPairNames()
    {
    	OSClient os = yaoClientService.getOsClient();
    	if(os != null)
    		return computeService.getKeyPairNames(os);
    	else
    		return Collections.<String>emptyList();
    }
    
    @RequestMapping("/securitygroup/list")
    public List<YaoSecurityGroup> getSecurityGroupList()
    {
    	OSClient os = yaoClientService.getOsClient();
    	if(os != null)
    		return computeService.getSecurityGroupList(os);
    	else
    		return Collections.<YaoSecurityGroup>emptyList();
    }
    
    @RequestMapping("/flavor/list")
    public List<YaoFlavor> getFlavorList(){
    	OSClient os = yaoClientService.getOsClient();
    	if(os != null)
    		return computeService.getFlavorList(os);
    	else
    		return Collections.<YaoFlavor>emptyList();
    }
   
    @RequestMapping("/instances/{serverId}/consolelog")
    public String getConsoleLogs(@PathVariable("serverId") String serverId) {
        OSClient os = yaoClientService.getOsClient();
        if(os != null)
        	return computeService.getConsoleLogs(os, serverId);
        else
        	return null;
    }
    
    @RequestMapping("/instances/{serverId}")
    public YaoInstance getInstance(@PathVariable("serverId") String serverId) {
        OSClient os = yaoClientService.getOsClient();
        if(os != null)
        	return computeService.getInstance(os, serverId);
        else
        	return null;
    }
    
    @RequestMapping("/limits")
    public YaoLimits getLimits() {
    	OSClient os = yaoClientService.getOsClient();
    	if(os != null)
    		return computeService.getDigestLimits(os);
    	else
    		return null;
    }
    
    @RequestMapping("/flavors")
    public YaoLimits getFlavors() {
        OSClient os = yaoClientService.getOsClient();
        if(os != null)
            return computeService.getDigestLimits(os);
        else
            return null;
    }
    
    @RequestMapping("/flavor/list/names")
    public List<String> getFlavorNames()
    {
    	OSClient os = yaoClientService.getOsClient();
        if(os != null)
            return computeService.getFlavorNames(os);
        else
        	return Collections.<String>emptyList();
    }
}