package com.alu.omc.oam.rest.os.controller;

import javax.annotation.Resource;

import org.openstack4j.model.network.SecurityGroup;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.rest.os.domain.instance.detail.YaoInstanceDetail;
import com.alu.omc.oam.rest.os.service.InstanceDetailService;
import com.alu.omc.oam.rest.os.service.SecGroupDetailService;

@RestController
@RequestMapping("/nfv/os/detail")
public class DetailController {
	
	@Resource
	InstanceDetailService instanceDetailService;
	@Resource
	SecGroupDetailService secGroupDetailService;
	
	@RequestMapping("/instance/{instanceId}")
	public YaoInstanceDetail getYaoInstanceDetail(@PathVariable String instanceId){
		YaoInstanceDetail detail = instanceDetailService.getInstanceDetail(instanceId);
		return detail;
	}
	
	@RequestMapping("/secGroup/{secGroupId}")
	public SecurityGroup getSecGroupDetail(@PathVariable String secGroupId){
		return secGroupDetailService.getSecGroupDetail(secGroupId);
	}
}
