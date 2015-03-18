package com.alu.omc.oam.rest.os.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.rest.os.domain.compute.YaoImage;
import com.alu.omc.oam.rest.os.service.GlanceService;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@RestController
@RequestMapping("/nfv/os/glance")
public class GlanceController {
	@Resource
	private YaoOsClientService yaoOsClientService;
	@Resource
	private GlanceService glanceService;

	public void setYaoClientService(YaoOsClientService yaoOsClientService) {
		this.yaoOsClientService = yaoOsClientService;
	}
	
	@RequestMapping("/image/list")
	public List<YaoImage> getImageList()
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return glanceService.getImageList(os);
		else
			return Collections.<YaoImage>emptyList();
	}
	
	@RequestMapping("/image/list/names")
	public List<String> getImageNames()
	{
		OSClient os = yaoOsClientService.getOsClient();
		if(os != null)
			return glanceService.getImageNames(os);
		else
			return Collections.<String>emptyList();
	}

}
