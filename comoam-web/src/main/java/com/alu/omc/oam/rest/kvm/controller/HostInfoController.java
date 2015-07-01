package com.alu.omc.oam.rest.kvm.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.service.HostService;

@RestController 
public class HostInfoController
{
    @Resource
    private HostService hostService;
    @RequestMapping(value="/host/bridge", method=RequestMethod.GET)
    public boolean  ping(@ModelAttribute("host") String host) 
    {
    	return hostService.ping((String)host);
    }
}
