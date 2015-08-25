package com.alu.omc.oam.rest.kvm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.util.COMTimeZone;
import com.alu.omc.oam.util.Timezone;

@RestController 
public class SystemInfoController
{

    @RequestMapping(value="/sysinfo/timezone", method=RequestMethod.GET)
    public Timezone[]  timezone() 
    {
    	return COMTimeZone.getTimeZone();
    }
    @RequestMapping(value="/sysinfo/hosttz", method=RequestMethod.GET)
    public Timezone hostTZ()
    {
    	return COMTimeZone.getHostTZ();
    }
}
