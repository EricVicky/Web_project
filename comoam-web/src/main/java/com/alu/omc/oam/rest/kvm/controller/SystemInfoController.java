package com.alu.omc.oam.rest.kvm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.util.COMTimeZone;

@RestController 
public class SystemInfoController
{

    @RequestMapping(value="/sysinfo/timezone", method=RequestMethod.GET)
    public String[]  timezone() 
    {
    	return COMTimeZone.getTimeZone();
    }

}
